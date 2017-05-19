package com.csair.csairmind.hunter.master.service;


import com.csair.csairmind.hunter.common.context.ApiContext;

/**
 * 具体的实现服务处理的基类
 * 所有具体要求继承此基类
 * Created by zhangcheng
 */
public abstract class BasicApiService implements IApiService {
    private ApiContext apiContext;

    @Override
    public ApiContext getApiContext() {
        return this.apiContext;
    }


    public void setApiContext(ApiContext apiContext) {
        this.apiContext = apiContext;
    }
}
