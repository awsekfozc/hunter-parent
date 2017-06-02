package com.csair.csairmind.hunter.scheduler;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.csair.csairmind.hunter.common","com.csair.csairmind.hunter.scheduler"})
public class Bootstrap {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Bootstrap.class, args);
    }
}