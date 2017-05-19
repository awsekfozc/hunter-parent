package com.csair.csairmind.hunter.master.service;


import com.csair.csairmind.hunter.common.constant.SprderConstants;
import com.csair.csairmind.hunter.common.enums.OperateCodeHolder;
import com.csair.csairmind.hunter.common.plug.RedisServiceImpl;
import com.csair.csairmind.hunter.common.request.ApiRequest;
import com.csair.csairmind.hunter.common.response.ApiResponse;
import com.csair.csairmind.hunter.common.response.BeatResponse;
import com.csair.csairmind.hunter.common.util.JsonUtil;
import com.csair.csairmind.hunter.common.vo.MachineInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 心跳服务
 * Created by zhangcheng
 */
@Slf4j
@Component
public class BeatService extends BasicApiService {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    RedisServiceImpl redisServiceImpl;


    @Override
    public ApiResponse execute(ApiRequest request) {
        String machineId = this.getApiContext().getMachineId();
        BeatResponse response = new BeatResponse();
        if (redisServiceImpl.hexists(SprderConstants.MACHINE_QUEUE_PREFIX, machineId)) {
            String json = redisServiceImpl.hget(SprderConstants.MACHINE_QUEUE_PREFIX, machineId);
            MachineInfo machineInfo = (MachineInfo) JsonUtil.toBean(json, MachineInfo.class);
            String updateTime = df.format(new Date());
            machineInfo.setUpdateTime(updateTime);
            redisServiceImpl.hset(SprderConstants.MACHINE_QUEUE_PREFIX, machineId, JsonUtil.fromObject(machineInfo));
            response.setOperateCodeHolder(OperateCodeHolder.BEAT_SUCCESS);
            response.setOk(true);
        } else {
            response.setOk(false);
            log.warn("收到心跳处理失败!...");
        }
        return response;
    }

}

