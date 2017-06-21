package com.csair.csairmind.hunter.spider.increment;

import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.vo.ResourceRule;
import com.csair.csairmind.hunter.common.vo.Rule;
import com.csair.csairmind.hunter.spider.factory.RedisFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static com.csair.csairmind.hunter.common.constant.SprderConstants.R_RESOURCE_TASK;

/**
 * Created by fate
 * 资源解析任务增量
 */
public class ResourceTaskIncrement implements TaskIncrement {

    private JedisPool pool = RedisFactory.getInstance();
    private ResourceRule resourceRule;

    @Override
    public void put(Rule rule) {
        Jedis jedis = this.pool.getResource();
        this.resourceRule = (ResourceRule) rule;
        String paging_reg = resourceRule.getPaging_reg();
        int now_page_mun = resourceRule.getNow_page_mun();
        int max_page_mun = resourceRule.getMax_page_num();
        if (now_page_mun + 1 <= max_page_mun) {
            paging_reg = paging_reg.replace("${d}", (now_page_mun + 1) + "");
            resourceRule.setNow_page_mun(now_page_mun + 1);
            resourceRule.setUrl(paging_reg);
            //提交到任务池
            jedis.lpush(R_RESOURCE_TASK, JSON.toJSONString(resourceRule));
        }

    }
}
