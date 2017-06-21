package com.csair.csairmind.hunter.master.monitor;

import com.csair.csairmind.hunter.common.config.AppValidateInfo;
import com.csair.csairmind.hunter.common.plug.IRedisService;
import com.csair.csairmind.hunter.common.util.JsonUtil;
import com.csair.csairmind.hunter.common.vo.MachineInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.csair.csairmind.hunter.common.constant.SprderConstants.MACHINE_QUEUE_PREFIX;

/**
 * Created by fate
 * 监控在线机器情况
 * 如果机器心跳不存在了，证明下线，之前占用的资源归还，删除该在线机器
 */
@Slf4j
@Component
public class MonitoringMachineScheduler {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    AppValidateInfo appValidateInfo;

    @Autowired
    IRedisService redisService;

    @Scheduled(fixedRate = 10000)
    public void monitoring() {
        log.warn("开始检查集群节点心跳状态");
        try {
            Map<String, String> map = redisService.hgetAll(MACHINE_QUEUE_PREFIX);
            for (String machine_id : map.keySet()) {
                MachineInfo info = (MachineInfo) JsonUtil.toBean(map.get(machine_id), MachineInfo.class);
                String updatetime = info.getUpdateTime();
                Date updateTime = df.parse(updatetime);
                if ((updateTime.getTime() + 30 * 1000) < new Date().getTime()) {
                    //下线机器
                    log.warn("检测到心跳超时机器[{}]，执行下线操作", info);
                    redisService.hdel(MACHINE_QUEUE_PREFIX, machine_id);
                }
            }
        } catch (Exception e) {
            log.error("监控节点状态出错", e);
        }
    }
}