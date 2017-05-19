package com.csair.csairmind.hunter.client;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.csair.csairmind.hunter.common"})
public class TestBootstrap {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestBootstrap.class, args);
    }
}