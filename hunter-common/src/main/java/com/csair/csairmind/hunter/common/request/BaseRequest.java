package com.csair.csairmind.hunter.common.request;

import java.text.SimpleDateFormat;

/**
 * Created by zhangcheng.
 */
public abstract class BaseRequest implements ApiRequest {

    @Override
    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 时间戳
     */
    protected String timestamp;


}
