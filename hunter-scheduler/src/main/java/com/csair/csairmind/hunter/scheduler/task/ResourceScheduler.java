package com.csair.csairmind.hunter.scheduler.task;

import static com.csair.csairmind.hunter.common.constant.SprderConstants.R_TASK_QUEUE;

import com.csair.csairmind.hunter.common.util.JsonUtil;
import com.csair.csairmind.hunter.common.plug.IRedisService;
import com.csair.csairmind.hunter.common.vo.SpriderTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhangcheng
 * 生成资源解析任务
 */
@Slf4j
@Component
public class ResourceScheduler {

    @Autowired
    IRedisService redisService;

    @Scheduled(fixedRate = 3000)
    public void analysisTask() throws InterruptedException {
        Map<String, String> dataMap = redisService.hgetAll(R_TASK_QUEUE);
        for (String key : dataMap.keySet()) {
            SpriderTask task = (SpriderTask) JsonUtil.toBean(dataMap.get(key), SpriderTask.class);
            task.getRequest_time();
        }
        log.info("执行定时任务");
    }
}
