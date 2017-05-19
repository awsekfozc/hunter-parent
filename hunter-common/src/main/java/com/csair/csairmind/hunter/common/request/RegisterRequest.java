package com.csair.csairmind.hunter.common.request;


import com.csair.csairmind.hunter.common.ApiHolder;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 机器注册
 * Created by zhengcheng
 */
@Data
public class RegisterRequest extends BaseRequest implements Serializable {

    private String ip;
    private String mac;
    private int pid;
    private String usename;
    private String regTime;
    private String updateTime;

    @Override
    public HashMap<String, String> getSignParameters() {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("ip", ip);
        parameters.put("mac", mac);
        parameters.put("pid", pid + "");
        return parameters;

    }

    @Override
    public String getApiName() {
        return ApiHolder.REGISTER;
    }
}
