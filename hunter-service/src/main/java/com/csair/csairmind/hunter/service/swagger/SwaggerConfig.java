package com.csair.csairmind.hunter.service.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by zhangcheng on 2017/3/24.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.csair.csairmind.hunter.service.api"))
                .paths(PathSelectors.any()).build()
                .useDefaultResponseMessages(false).enable(true);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("爬虫服务接口中心").version("1.0-SNAPSHOT").build();
    }
}
