package com.csair.csairmind.hunter.service.api;

import com.csair.csairmind.hunter.service.request.CheckUserRequest;
import com.csair.csairmind.hunter.service.result.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


/**
 * Created by zhangcheng
 */
@RestController
@Slf4j
@Api(tags = {"cookie插件接口"})
@RequestMapping(value = "/cookie")
public class CookiesService {

    @ApiOperation(value = "测试自动登陆的账号密码")
    @PostMapping("/test")
    public ResultData testUserAndPwd(@Valid @RequestBody List<CheckUserRequest> request, HttpServletResponse httpServletResponse) {
        return null;
    }
}
