package com.csair.csairmind.hunter.common.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangcheng
 */
@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "hunter.app",locations = "classpath:application.properties")
public class AppValidateInfo {
    private String appKey;
    private String appSecret;
    private Integer heartBeatTime;
    private Integer exception_wait_mill_seconds;
    private Integer applyTaskTime;
}
