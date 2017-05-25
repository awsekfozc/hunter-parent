package com.csair.csairmind.hunter.spider.schedule;


import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.constant.SprderConstants;
import com.csair.csairmind.hunter.spider.factory.RedisFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * Created by zhangcheng
 * 资源解析任务调度器
 */
public class ResourceTaskScheduler implements DistinctScheduler {

    private JedisPool pool = RedisFactory.getInstance();

    @Override
    public void push(Request request, Task task) {
        Jedis jedis = this.pool.getResource();
        try {
            jedis.lpush(SprderConstants.R_DETAILS_TASK_KEY, request.getUrl());
            jedis.hset(SprderConstants.R_DETAILS_TASK, request.getUrl(), JSON.toJSONString(request.getExtras()));
        } finally {
            this.pool.returnResource(jedis);
        }
    }

    @Override
    public Request poll(Task task) {
        return null;
    }

    @Override
    public void pushDistinctQueue(String info) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hset(SprderConstants.R_DISTINCT_QUEUE, info,"");
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public void pushTask(String taskStr) {

        Jedis jedis = this.pool.getResource();
        try {
            jedis.lpush(SprderConstants.R_DETAILS_TASK, taskStr);
        } finally {
            this.pool.returnResource(jedis);
        }
    }
}
