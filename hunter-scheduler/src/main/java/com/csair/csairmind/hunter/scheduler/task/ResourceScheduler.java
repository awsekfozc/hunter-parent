package com.csair.csairmind.hunter.scheduler.task;

import static com.csair.csairmind.hunter.common.constant.SprderConstants.R_TASK_QUEUE;
import static com.csair.csairmind.hunter.common.constant.SprderConstants.R_RESOURCE_TASK;
import static com.csair.csairmind.hunter.common.enums.MatchCharEnums.PAGE_MATCH;
import static com.csair.csairmind.hunter.common.enums.MatchCharEnums.WORD_MATCH;
import static com.csair.csairmind.hunter.common.enums.SpriderEnums.DETAILS_TASK;
import static com.csair.csairmind.hunter.common.enums.SpriderEnums.LIST_DETAILS_TASK;
import static com.csair.csairmind.hunter.common.enums.SpriderEnums.LIST_WORK_TASK;

import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.enums.SpriderEnums;
import com.csair.csairmind.hunter.common.util.DateUtils;
import com.csair.csairmind.hunter.common.util.JsonUtil;
import com.csair.csairmind.hunter.common.plug.IRedisService;
import com.csair.csairmind.hunter.common.vo.DetailsRule;
import com.csair.csairmind.hunter.common.vo.ResourceRule;
import com.csair.csairmind.hunter.common.vo.SpriderTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
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
        log.info("执行定时任务");
        Map<String, String> dataMap = redisService.hgetAll(R_TASK_QUEUE);
        for (String key : dataMap.keySet()) {
            try {
                SpriderTask task = (SpriderTask) JsonUtil.toBean(dataMap.get(key), SpriderTask.class);
                Date requestTime = DateUtils.parseDate(task.getRequest_time());
                Date nowtime = new Date(System.currentTimeMillis());
                if (nowtime.getTime() - requestTime.getTime() > task.getIncrement_rule()) {
                    if (task.getTask_type() == LIST_DETAILS_TASK.getCode()) {//列表+详情类型
                        ResourceRule resourceRule = new ResourceRule();
                        //复制属性到资源解析任务规则
                        BeanUtils.copyProperties(task, resourceRule);
                        //提交时间与当前时间之差超过增量间隔
                        task.setRequest_time(DateUtils.getDateTime());//更新提交时间
                        log.info("更新提交时间");
                        resourceRule.setNow_page_mun(1);//设置当前页
                        resourceRule.setMax_page_num(task.getMax_page_size());//设置最大页数
                        redisService.lpush(R_RESOURCE_TASK, JSON.toJSONString(resourceRule));
                        redisService.hset(R_TASK_QUEUE, task.getTask_id(), JSON.toJSONString(task));
                    } else if (task.getTask_type() == LIST_WORK_TASK.getCode()) {//关键字+列表
                        String[] wrods = task.getSearch_wrods().split(",");
                        ResourceRule resourceRule = new ResourceRule();
                        BeanUtils.copyProperties(task, resourceRule);
                        resourceRule.setMax_page_num(task.getMax_page_size());//设置最大页数
                        resourceRule.setNow_page_mun(1);//设置当前页
                        for (String wrod : wrods) {
                            wrod = URLEncoder.encode(wrod, task.getWrod_code());
                            String page_reg = task.getPage_reg().replace(WORD_MATCH.getMatch(), wrod);
                            String url = page_reg.replace(PAGE_MATCH.getMatch(), "1");
                            resourceRule.setPaging_reg(page_reg);//将分页表达式的关键字占位符处理掉
                            resourceRule.setUrl(url);
                            redisService.lpush(R_RESOURCE_TASK, JSON.toJSONString(resourceRule));
                        }
                        task.setRequest_time(DateUtils.getDateTime());//更新提交时间
                        redisService.hset(R_TASK_QUEUE, task.getTask_id(), JSON.toJSONString(task));
                    } else if (task.getTask_type() == DETAILS_TASK.getCode()) {//详情
                        DetailsRule rule = new DetailsRule();
                    }
                }
                task.getRequest_time();
            } catch (Exception ex) {
                log.error("生成资源出错", ex);
            }
        }
    }
}