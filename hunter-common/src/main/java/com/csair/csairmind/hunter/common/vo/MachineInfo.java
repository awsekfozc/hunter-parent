package com.csair.csairmind.hunter.common.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by zhangcheng
 */
@Data
@ToString
public class MachineInfo implements Serializable {
    /**
     * 机器ID
     */
    private String machineId;

    /**
     * 会话密钥，可用于加解密
     */
    private String sessionKey;

    private String mac;
    private String ip;
    private String regTime;
    private String updateTime;
}
