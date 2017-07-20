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

    //mac地址
    private String mac;
    //ip
    private String ip;
    //注册时间
    private String regTime;
    //更新时间
    private String updateTime;
}
