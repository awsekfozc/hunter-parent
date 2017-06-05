package com.csair.csairmind.hunter.common.response;

import lombok.Data;

/**
 * Created by zhangcheng
 * 机器注册心跳响应
 */
@Data
public class RegisterResponse extends ApiResponse {

    /**
     * 机器ID
     */
    private String machineId;
    /**
     * 会话key
     */
    private String sessionKey;
    /**
     * 加密类型，0为不加密
     */
    private int secrType;

}
