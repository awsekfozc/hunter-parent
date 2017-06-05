package com.csair.csairmind.hunter.common.request;


import com.csair.csairmind.hunter.common.response.ApiResponse;

import java.util.HashMap;

/**
 * 用于服务请求的参数接口
 * Created by zhengcheng
 */
public interface ApiRequest<T extends ApiResponse> {


    /**
     * 要调用的服务名
     *
     * @return
     */
    public String getApiName();

    /**
     * 需要签名的参数
     * @return
     */
    public HashMap<String, String> getSignParameters();


    /**
     * 时间戳
     *
     * @return
     */
    public String getTimestamp();

    public void setTimestamp(String timestamp);
}

