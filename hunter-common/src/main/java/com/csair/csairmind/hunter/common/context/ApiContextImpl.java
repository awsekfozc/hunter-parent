package com.csair.csairmind.hunter.common.context;


import com.csair.csairmind.hunter.common.vo.MachineInfo;

/**
 * Created by zhengcheng
 */
public class ApiContextImpl implements ApiContext {
    private String machineId;
    private MachineInfo machineInfo;

    @Override
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    @Override
    public String getMachineId() {
        return machineId;
    }

    @Override
    public void setMachineInfo(MachineInfo machineInfo) {
        this.machineInfo = machineInfo;
    }

    @Override
    public MachineInfo getMachineInfo() {
        return machineInfo;
    }
}
