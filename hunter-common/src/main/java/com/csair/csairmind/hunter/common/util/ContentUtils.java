package com.csair.csairmind.hunter.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhangcheng
 */
public class ContentUtils {

    public static String removeUrlContent(String value) {
        if (StringUtils.isNotBlank(value))
            value = value.replaceAll("[<](.*?)[>]", "");
        return value;
    }
}
