package com.csair.csairmind.hunter.common.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangcheng on 2017/6/5 0005.
 */
@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "spring.redis", locations = "classpath:dev/application.properties")
public class RedisConfigVo {
    private String hostName;
}
