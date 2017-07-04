package com.csair.csairmind.hunter.scheduler;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.csair.csairmind.hunter.common","com.csair.csairmind.hunter.scheduler"})
public class Bootstrap {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Bootstrap.class, args);
    }
}