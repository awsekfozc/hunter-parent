package com.csair.csairmind.hunter.common;


import com.csair.csairmind.hunter.common.exception.ApiException;
import com.csair.csairmind.hunter.common.request.ApiRequest;
import com.csair.csairmind.hunter.common.request.OperateResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by zhangcheng
 */
@Component
public interface ApiClient {
    OperateResult execute(ApiRequest request) throws ApiException;
}
