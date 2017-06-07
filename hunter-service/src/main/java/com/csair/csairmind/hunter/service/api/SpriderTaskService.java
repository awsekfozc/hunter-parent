package com.csair.csairmind.hunter.service.api;

import com.csair.csairmind.hunter.common.util.BeanToMapUtil;
import com.csair.csairmind.hunter.common.vo.DetailsRule;
import com.csair.csairmind.hunter.common.vo.ResourceRule;
import com.csair.csairmind.hunter.common.vo.SingleDataVo;
import com.csair.csairmind.hunter.service.request.SpriderTestRequest;
import com.csair.csairmind.hunter.service.response.SpriderTestResponse;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangcheng
 */
@RestController
@Slf4j
@Api(tags = {"爬虫任务接口"})
@RequestMapping(value = "/sprider")
public class SpriderTaskService {

    @Value("${max_test_size}")
    private int max_test_size;

    @ApiOperation(value = "测试爬虫任务")
    @PostMapping("/testSprider")
    public ResultData<List<Map<String, Object>>> test(@Valid @RequestBody SpriderTestRequest spriderTestRequest, @RequestHeader HttpHeaders headers, HttpServletResponse httpServletResponse) {
        ResourceRule task = new ResourceRule();
        List<Map<String, Object>> resultData = new ArrayList<>();
        try {
            BeanUtils.copyProperties(spriderTestRequest, task);
            TestTaskScheduler scheduler = new TestTaskScheduler();//使用测试爬虫scheduler
            //执行资源解析任务
            ExpandSpider.create(new ResourcesProcessor())
                    .setScheduler(scheduler)
                    .setStartRequest(task.getUrl(), task)
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
            System.out.println(e);
            return ResultData.getFailResult("测试爬虫任务失败" + e.getMessage());
        }
        return ResultData.getSuccessResult(resultData, "爬虫测试成功");
    }


    @ApiOperation(value = "提交爬虫任务")
    @PostMapping("/submit")
    public void taskSubmit(String sysCode, HttpServletResponse httpServletResponse) {

    }
}
