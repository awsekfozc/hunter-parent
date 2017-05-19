package com.csair.csairmind.hunter.client.service;


import com.csair.csairmind.hunter.client.api.DefaultApiClient;
import com.csair.csairmind.hunter.client.content.DefaultApplicationContext;
import com.csair.csairmind.hunter.common.config.AppValidateInfo;
import com.csair.csairmind.hunter.common.enums.OperateCodeHolder;
import com.csair.csairmind.hunter.common.request.OperateResult;
import com.csair.csairmind.hunter.common.request.RegisterRequest;
import com.csair.csairmind.hunter.common.response.RegisterResponse;
import com.csair.csairmind.hunter.common.spring.ApplicationContextProvider;
import com.csair.csairmind.hunter.common.util.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 方便重复调用，只是简单的包了一下服务接口
 * Created by zhangcheng
 */
@Slf4j
@Service
public class WrapService {


    @Autowired
    AppValidateInfo appValidateInfo;

    @Autowired
    DefaultApiClient defaultApiClient;

    /**
     * 机器注册
     *
     * @return
     */
    public boolean register() {
        try {
            defaultApiClient.appKey = appValidateInfo.getAppKey();
            defaultApiClient.appSecret = appValidateInfo.getAppSecret();

            RegisterRequest request = new RegisterRequest();
            request.setIp(ApiUtils.getLocalIp());
            request.setMac(ApiUtils.getLocalMac());
            OperateResult result = defaultApiClient.execute(request);
            if (result.getOperateCodeHolder().equals(OperateCodeHolder.REGISTER_SUCCESS)) {
                RegisterResponse response = (RegisterResponse) result.getResponse();
                DefaultApplicationContext.MACHINEID = response.getMachineId();
                DefaultApplicationContext.SESSIONKEY = response.getSessionKey();
                log.info("注册成功!");
                return true;
            } else {
                log.error(String.format("机器注册结果：[%s]", result.getOperateCodeHolder()));
            }
        } catch (Exception ex) {
            log.error("机器注册异常", ex);
        }
        return false;
    }
}
