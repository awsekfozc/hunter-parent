package com.csair.csairmind.hunter.service.api;

import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.plug.IRedisService;
import com.csair.csairmind.hunter.service.request.MachineInfoRequest;
import com.csair.csairmind.hunter.service.result.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.csair.csairmind.hunter.common.constant.SprderConstants.MACHINE_QUEUE_PREFIX;


/**
 * Created by zhangcheng
 */
@RestController
@Slf4j
@Api(tags = {"集群信息服务"})
@RequestMapping(value = "/machine")
public class MachineService {

    @Autowired
    IRedisService redisServiceImpl;

    @ApiOperation(value = "获取集群所有节点")
    @PostMapping("/info")
    public ResultData taskUserAndPwd(HttpServletResponse httpServletResponse) {
        List<Map<String,String>> resultList = new ArrayList<>();
        Map<String, String> machineDatas = redisServiceImpl.hgetAll(MACHINE_QUEUE_PREFIX);
        for(String key:machineDatas.keySet()){
            resultList.add(JSON.parseObject(machineDatas.get(key),Map.class));
        }
        return ResultData.getSuccessResult(resultList, "查询成功");
    }
}
