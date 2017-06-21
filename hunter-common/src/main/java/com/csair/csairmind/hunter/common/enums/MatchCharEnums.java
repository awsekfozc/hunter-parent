package com.csair.csairmind.hunter.common.enums;

import lombok.Getter;

/**
 * Created by zhangcheng
 * 匹配字符枚举，用于字符串占位与调换
 */
public enum MatchCharEnums {

    PAGE_MATCH("${page}"),
    WORD_MATCH("${word}");


    @Getter
    private final String match;

    MatchCharEnums(String match) {
        this.match = match;
    }
}
