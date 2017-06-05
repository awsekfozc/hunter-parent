package com.csair.csairmind.hunter.scheduler.task;

import static com.csair.csairmind.hunter.common.constant.SprderConstants.R_TASK_QUEUE;
import static com.csair.csairmind.hunter.common.constant.SprderConstants.R_RESOURCE_TASK;

import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.util.DateUtils;
import com.csair.csairmind.hunter.common.util.JsonUtil;
import com.csair.csairmind.hunter.common.plug.IRedisService;
import com.csair.csairmind.hunter.common.vo.SpriderTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Created by zhangcheng
 * 资源生成
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
            try {
                SpriderTask task = (SpriderTask) JsonUtil.toBean(dataMap.get(key), SpriderTask.class);
                Date requestTime = DateUtils.parseDate(task.getRequest_time());
                Date nowtime = new Date(System.currentTimeMillis());
                //提交时间与当前时间之差超过增量间隔
                if (nowtime.getTime() - requestTime.getTime() > task.getIncrement_rule()) {
                    task.setRequest_time(DateUtils.getDateTime());
                    log.info("更新提交时间");
                    System.out.println(JSON.toJSONString(task));
                    redisService.lpush(R_RESOURCE_TASK, JSON.toJSONString(task));
                    redisService.hset(R_TASK_QUEUE, task.getTask_id(), JSON.toJSONString(task));
                }
                task.getRequest_time();
            } catch (Exception ex) {
                log.error("生成资源出错", ex);
            }
        }
        log.info("执行定时任务");
    }
}