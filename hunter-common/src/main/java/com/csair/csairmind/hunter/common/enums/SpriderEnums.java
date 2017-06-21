package com.csair.csairmind.hunter.common.enums;

import lombok.Getter;

/**
 * Created by zhangcheng
 */
public enum SpriderEnums {

    LIST_WORK_TASK(1, "关键字+列表"),
    LIST_DETAILS_TASK(2, "列表+详情"),
    DETAILS_TASK(3, "详情"),
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
