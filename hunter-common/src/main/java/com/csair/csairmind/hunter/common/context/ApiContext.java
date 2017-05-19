package com.csair.csairmind.hunter.common.context;


import com.csair.csairmind.hunter.common.vo.MachineInfo;

/**
 * 封装每个请求的上下文，方便后面的service存读使用
 * Created by zhengcheng
 */
public interface ApiContext {
    public void setMachineId(String machineId);

    public String getMachineId();

    public void setMachineInfo(MachineInfo machineInfo);

    public MachineInfo getMachineInfo();
}
