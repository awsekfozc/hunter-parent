package com.csair.csairmind.hunter.spider.schedule;


import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.constant.SprderConstants;
import org.apache.commons.codec.digest.DigestUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * Created by zhangcheng
 * 资源解析任务调度器
 */
public class ResourceTaskScheduler implements Scheduler {

    protected JedisPool pool;

    public ResourceTaskScheduler(String host) {
        this(new JedisPool(new JedisPoolConfig(), host));
    }

    public ResourceTaskScheduler(JedisPool pool) {
        this.pool = pool;
    }

    @Override
    public void push(Request request, Task task) {
        Jedis jedis = this.pool.getResource();
        try {
            jedis.lpush(SprderConstants.R_DETAILS_TASK, request.getUrl());
        } finally {
            this.pool.returnResource(jedis);
        }
    }

    @Override
    public Request poll(Task task) {
        return null;
    }
}
