package com.csair.csairmind.hunter.master;

import com.csair.csairmind.hunter.common.plug.IRedisService;
import com.csair.csairmind.hunter.master.monitor.MonitoringMachineThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 任务启动类
 * Created by zhangcheng
 */
@Component
@Slf4j
public class StartTask {

    @Autowired
    IRedisService redisService;

    @Autowired
    MonitoringMachineThread monitoringMachineThread;

    //定义在构造方法完毕后，执行这个初始化方法
    public @PostConstruct void init() {
        log.info("启动监控节点状态线程");
        monitoringMachineThread.start();
    }
}