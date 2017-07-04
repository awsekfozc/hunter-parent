package com.csair.csairmind.hunter.service.api;

import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.plug.IRedisService;
import com.csair.csairmind.hunter.common.util.DateUtils;
import com.csair.csairmind.hunter.common.vo.DetailsRule;
import com.csair.csairmind.hunter.common.vo.ResourceRule;
import com.csair.csairmind.hunter.common.vo.SpriderTask;
import com.csair.csairmind.hunter.service.request.SpriderSubmitRequest;
import com.csair.csairmind.hunter.service.request.SpriderTestRequest;
import com.csair.csairmind.hunter.service.result.ResultData;
import com.csair.csairmind.hunter.spider.ExpandSpider;
import com.csair.csairmind.hunter.spider.pipeline.SpriderTestPipeline;
import com.csair.csairmind.hunter.spider.processor.currency.DetailsSingleProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.ResourcesProcessor;
import com.csair.csairmind.hunter.spider.schedule.ResourceTaskScheduler;
import com.csair.csairmind.hunter.spider.schedule.TestTaskScheduler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.csair.csairmind.hunter.common.constant.SprderConstants.R_TASK_QUEUE;
import static com.csair.csairmind.hunter.common.enums.SpriderEnums.TASK_STATUS_SOTP;
import static com.csair.csairmind.hunter.common.enums.SpriderEnums.TASK_STATUS_START;

/**
 * Created by zhangcheng
 */
@RestController
@Slf4j
@Api(tags ="爬虫任务接口")
@RequestMapping(value = "/sprider")
public class SpriderTaskService {

    @Autowired
    IRedisService redisServiceImpl;

    @Value("${max_test_size}")
    private int max_test_size;

    @ApiOperation(value = "测试爬虫任务")
    @PostMapping("/test")
    public ResultData<List<Map<String, Object>>> testSprider(@Valid @RequestBody SpriderTestRequest spriderTestRequest, @RequestHeader HttpHeaders headers, HttpServletResponse httpServletResponse) {
        ResourceRule task = new ResourceRule();
        List<Map<String, Object>> resultData;
        try {
            BeanUtils.copyProperties(spriderTestRequest, task);
            TestTaskScheduler scheduler = new TestTaskScheduler();//使用测试爬虫scheduler
            //执行资源解析任务
            ExpandSpider.create(new ResourcesProcessor())
                    .setScheduler(scheduler)
                    .setStartRequest(task)
                    .run();

            //执行详情解析任务
            List<DetailsRule> detailsTasks = scheduler.getDetalisTask();
            SpriderTestPipeline pipeline = new SpriderTestPipeline();//使用测试爬虫pipeline
            ExpandSpider spider = ExpandSpider.create(new DetailsSingleProcessor())
                    .setScheduler(new ResourceTaskScheduler())
                    .setSpawnDistinct(false)
                    .setPipeline(pipeline);

            for (int i = 0; i < detailsTasks.size(); i++) {
                if (i == max_test_size)
                    break;
                spider.setStartRequest(detailsTasks.get(i).getUrl(), detailsTasks.get(i));
            }
            spider.run();
            resultData = pipeline.getDataList();
        } catch (Exception e) {
            return ResultData.getFailResult("测试爬虫任务失败" + e.getMessage());
        }
        return ResultData.getSuccessResult(resultData, "爬虫测试成功");
    }


    @ApiOperation(value = "提交爬虫任务")
    @PostMapping("/submit")
    public ResultData taskSubmit(@Valid @RequestBody SpriderSubmitRequest request, HttpServletResponse httpServletResponse) {
        SpriderTask task = new SpriderTask();
        BeanUtils.copyProperties(request, task);
        task.setTask_id("A" + System.currentTimeMillis());
        task.setCreate_time(DateUtils.getDateTime());
        task.setRequest_time(DateUtils.getDateTime());
        task.setTask_status(TASK_STATUS_START.getCode());
        redisServiceImpl.hset(R_TASK_QUEUE, task.getTask_id(), JSON.toJSONString(task));
        return ResultData.getSuccessResult("爬虫提交成功");
    }

    @ApiOperation(value = "停止爬虫任务")
    @GetMapping("/stop")
    public ResultData stopTask(@RequestParam String task_id, HttpServletResponse httpServletResponse) {
        try {
            SpriderTask task = JSON.parseObject(redisServiceImpl.hget(R_TASK_QUEUE, task_id), SpriderTask.class);
            task.setTask_status(TASK_STATUS_SOTP.getCode());
            redisServiceImpl.hset(R_TASK_QUEUE, task.getTask_id(), JSON.toJSONString(task));
            return ResultData.getSuccessResult("爬虫停止成功");
        }catch (NullPointerException ex){
            return ResultData.getFailResult("爬虫停止失败，任务不存在");
        }
    }
}
