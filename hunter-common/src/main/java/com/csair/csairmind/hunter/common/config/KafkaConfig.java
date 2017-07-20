package com.csair.csairmind.hunter.common.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by fate
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.kafka", locations = "classpath:dev/application.properties")
public class KafkaConfig {
    private String host;
    private String port;
    private String topic;
}
