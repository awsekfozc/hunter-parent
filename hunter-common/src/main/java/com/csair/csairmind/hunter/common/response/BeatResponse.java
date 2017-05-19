package com.csair.csairmind.hunter.common.response;

/**
 * Created by zhangcheng
 */
public class BeatResponse extends ApiResponse {

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    /**
     * 是否心跳成功
     */
    private boolean ok;
}

