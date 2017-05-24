package com.csair.csairmind.hunter.spider.distinct;

import com.csair.csairmind.hunter.common.constant.SprderConstants;
import com.csair.csairmind.hunter.spider.factory.RedisFactory;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by zhangcheng
 */
@Slf4j
public class UrlDistinct implements Distinct {

    private JedisPool pool = RedisFactory.getInstance();

    public boolean isDistinct(String content) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hexists(SprderConstants.R_DISTINCT_QUEUE, content);
        } catch (Exception ex) {
            log.error("URL获取去重信息错误：{}", ex.getMessage());
        } finally {
            pool.returnResource(jedis);
        }
        return false;
    }
}
