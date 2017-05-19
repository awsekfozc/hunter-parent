package com.csair.csairmind.hunter.master.service;


import com.csair.csairmind.hunter.common.constant.SprderConstants;
import com.csair.csairmind.hunter.common.enums.OperateCodeHolder;
import com.csair.csairmind.hunter.common.plug.IRedisService;
import com.csair.csairmind.hunter.common.plug.RedisServiceImpl;
import com.csair.csairmind.hunter.common.request.ApiRequest;
import com.csair.csairmind.hunter.common.request.RegisterRequest;
import com.csair.csairmind.hunter.common.response.ApiResponse;
import com.csair.csairmind.hunter.common.response.RegisterResponse;
import com.csair.csairmind.hunter.common.spring.ApplicationContextProvider;
import com.csair.csairmind.hunter.common.util.JsonUtil;
import com.csair.csairmind.hunter.common.vo.MachineInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 机器注册
 * Created by zhangcheng
 */
@Slf4j
@Component
public class RegisterService extends BasicApiService {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    RedisServiceImpl redisServiceImpl;

    @Override
    public ApiResponse execute(ApiRequest request) {
        RegisterRequest req = (RegisterRequest) request;
        String machineId = UUID.randomUUID().toString().replace("-", "");
        String sessionKey = UUID.randomUUID().toString().replace("-", "");

        MachineInfo machineInfo = new MachineInfo();
        machineInfo.setIp(req.getIp());
        machineInfo.setMachineId(machineId);
        machineInfo.setMac(req.getMac());
        machineInfo.setSessionKey(sessionKey);
        String regeidtTime = df.format(new Date());
        machineInfo.setRegTime(regeidtTime);
        machineInfo.setUpdateTime(regeidtTime);

        //从尾部插入
        redisServiceImpl.hset(SprderConstants.MACHINE_QUEUE_PREFIX, machineId, JsonUtil.fromObject(machineInfo));
        log.info("新机器注册注册完毕，信息：" + machineInfo.getIp() + "|" + machineInfo.getMac() + "|" + machineId);
        RegisterResponse response = new RegisterResponse();
        response.setMachineId(machineId);
        response.setSessionKey(sessionKey);
        response.setSecrType(0);
        response.setOperateCodeHolder(OperateCodeHolder.REGISTER_SUCCESS);
        return response;
    }
}
