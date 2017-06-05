package com.csair.csairmind.hunter.client.work;

import com.csair.csairmind.hunter.client.content.DefaultApplicationContext;
import com.csair.csairmind.hunter.common.config.RedisConfigVo;
import com.csair.csairmind.hunter.common.request.DetalisTaskRequest;
import com.csair.csairmind.hunter.common.request.OperateResult;
import com.csair.csairmind.hunter.common.response.ApiResponse;
import com.csair.csairmind.hunter.common.response.DetalisTaskResponse;
import com.csair.csairmind.hunter.common.vo.DetailsTask;
import com.csair.csairmind.hunter.spider.ExpandSpider;
import com.csair.csairmind.hunter.spider.processor.currency.DetailsSingleProcessor;
import com.csair.csairmind.hunter.spider.schedule.ResourceTaskScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import static com.csair.csairmind.hunter.common.enums.OperateCodeHolder.DETALIS_TASK_SUCCESS;


/**
 * Created by zhangcheng
 */
@Slf4j
@Component
public class DetailsWork extends BaseThread {


    @Autowired
    RedisConfigVo redisConfigVo;

    @Override
    public void doing() {
        super.initClient();
        try {
            DetalisTaskRequest request = new DetalisTaskRequest();
            OperateResult result = defaultApiClient.execute(request);
            if (!result.getOperateCodeHolder().equals(DETALIS_TASK_SUCCESS)) {
                takeARest(1000);
            } else {
                ApiResponse response = result.getResponse();
                DetalisTaskResponse rsp = (DetalisTaskResponse) response;
                DetailsTask task = rsp.getTask();
                if (task == null) {
                    log.info("无详情解析任务");
                } else {
                    log.info("开始执行详情解析任务");
                    ExpandSpider.create(new DetailsSingleProcessor(task), new JedisPool(redisConfigVo.getHostName()))
                            .setScheduler(new ResourceTaskScheduler())
                            .setStartRequest(task.getUrl())
                            .setPipeline(new ConsolePipeline())
                            .run();
                }
            }
        } catch (Exception e) {
            log.error("申请任务失败，调用接口出错", e);
        }
    }
}
