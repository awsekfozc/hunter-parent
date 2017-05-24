package com.csair.csairmind.hunter.spider.factory;

import redis.clients.jedis.JedisPool;

/**
 * Created by zhangcheng
 * reids连接工厂
 */
public class RedisFactory {

    private static JedisPool pool;
    public static JedisPool getInstance(){
        return pool;
    }
    public static void init(JedisPool pool){
        RedisFactory.pool = pool;
    }
}
