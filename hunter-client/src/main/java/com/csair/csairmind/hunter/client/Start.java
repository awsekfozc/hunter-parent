package com.csair.csairmind.hunter.client;

import com.csair.csairmind.hunter.client.content.ApplicationContext;
import com.csair.csairmind.hunter.client.content.DefaultApplicationContext;
import com.csair.csairmind.hunter.common.inf.MgrService;
import com.csair.csairmind.hunter.common.plug.IRedisService;
import com.csair.csairmind.hunter.common.plug.RedisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * Created by zhangcheng
 * 任务启动
 */
@Component
@Slf4j
public class Start {

    @Autowired
    ApplicationContext defaltApplicationContext;

    public @PostConstruct void init() {
        //放入全局公共变量，以便引用
        defaltApplicationContext.start();
    }
}