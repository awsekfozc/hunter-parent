package com.csair.csairmind.hunter.service.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by xianyongjie on 2016/11/8.
 */
@RestController
@Slf4j
@Api(tags = {"首页接口"})
@RequestMapping(value = "/main/index")
public class AdminIndexService {


    @SuppressWarnings("unchecked")
	@ApiOperation(value = "IP测试")
    @GetMapping("/resource")
    public void resource(@RequestHeader("token") String token,String sysCode, HttpServletResponse httpServletResponse){

    }
}
