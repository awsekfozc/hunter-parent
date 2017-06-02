package com.csair.csairmind.hunter.service.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangcheng
 */
@RestController
@Slf4j
@Api(tags = {"爬虫任务测试接口"})
@RequestMapping(value = "/sprider")
public class SpriderTaskService {


	@ApiOperation(value = "测试爬虫任务")
    @GetMapping("/test")
    public void resource(String sysCode, HttpServletResponse httpServletResponse){

    }
}
