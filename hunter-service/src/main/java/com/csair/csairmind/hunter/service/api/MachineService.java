package com.csair.csairmind.hunter.service.api;

import com.csair.csairmind.hunter.service.request.MachineInfoRequest;
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


/**
 * Created by zhangcheng
 */
@RestController
@Slf4j
@Api(tags = {"集群信息服务"})
@RequestMapping(value = "/machine")
public class MachineService {

    @ApiOperation(value = "获取集群所有节点")
    @PostMapping("/info")
    public ResultData taskUserAndPwd(@Valid @RequestBody MachineInfoRequest machineInfoRequest, HttpServletResponse httpServletResponse) {
        return null;
    }
}
