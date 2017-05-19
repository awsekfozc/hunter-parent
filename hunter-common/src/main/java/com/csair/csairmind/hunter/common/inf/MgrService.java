package com.csair.csairmind.hunter.common.inf;

import com.csair.csairmind.hunter.common.request.ApiRequest;
import com.csair.csairmind.hunter.common.request.OperateResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhengcheng
 */
@Resource
public interface MgrService {
    /**
     * 用于接受客户端请求的服务
     *
     * @param appKey
     * @param machineId
     * @param sign
     * @param request
     * @return
     */
    public OperateResult execute(String appKey, String machineId, String sign, ApiRequest request);


}
