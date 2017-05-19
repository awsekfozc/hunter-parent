package com.csair.csairmind.hunter.common.request;


import com.csair.csairmind.hunter.common.ApiHolder;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 心跳请求
 * Created by zhangcheng
 */
public class BeatRequest extends BaseRequest implements Serializable {

    @Override
    public String getApiName() {
        return ApiHolder.BEAT;
    }

    @Override
    public HashMap<String, String> getSignParameters() {
        return null;
    }
}
