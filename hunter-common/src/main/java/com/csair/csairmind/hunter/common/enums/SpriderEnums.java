package com.csair.csairmind.hunter.common.enums;

import lombok.Getter;

/**
 * Created by zhangcheng
 */
public enum SpriderEnums {

    RESOURCE_PROCESSOR_TYPE(1, "资源解析任务"),
    DETAILS_PROCESSOR_TYPE(2, "详情解析任务"),
    DETAILS_LIST_PRO(2,"详情解析列表类型"),
    DETAILS_ALL_PRO(1,"详情解析单条类型"),
    TASK_STATUS_SOTP(2,"停止"),
    TASK_STATUS_START(1,"启动"),
    TASK_STATUS_RUN(2,"运行");



    @Getter
    private final Integer code;

    @Getter
    private final String msg;

    SpriderEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
