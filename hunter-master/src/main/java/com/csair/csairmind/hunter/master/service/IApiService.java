package com.csair.csairmind.hunter.master.service;


import com.csair.csairmind.hunter.common.context.ApiContext;
import com.csair.csairmind.hunter.common.request.ApiRequest;
import com.csair.csairmind.hunter.common.response.ApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 最顶层的接口类
 * Created by zhangcheng
 */
@Component
public interface IApiService {
    /**
     * 用于处理请求的接口
     *
     * @return
     */
    public ApiResponse execute(ApiRequest request);

    public ApiContext getApiContext();

    public void setApiContext(ApiContext apiContext);


}
