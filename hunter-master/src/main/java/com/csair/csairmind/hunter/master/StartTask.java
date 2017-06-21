package com.csair.csairmind.hunter.master;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 任务启动类
 * Created by zhangcheng
 */
@Component
@Slf4j
public class StartTask {

    //定义在构造方法完毕后，执行这个初始化方法
    public @PostConstruct void init() {
    }
}