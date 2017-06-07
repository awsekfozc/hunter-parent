package com.csair.csairmind.hunter.master.service;

import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.plug.RedisServiceImpl;
import com.csair.csairmind.hunter.common.request.ApiRequest;
import com.csair.csairmind.hunter.common.response.ApiResponse;
import com.csair.csairmind.hunter.common.response.DetalisTaskResponse;
import com.csair.csairmind.hunter.common.vo.DetailsRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.csair.csairmind.hunter.common.constant.SprderConstants.MACHINE_QUEUE_PREFIX;
import static com.csair.csairmind.hunter.common.constant.SprderConstants.R_DETAILS_TASK;
import static com.csair.csairmind.hunter.common.constant.SprderConstants.R_DETAILS_TASK_KEY;
import static com.csair.csairmind.hunter.common.enums.OperateCodeHolder.DETALIS_TASK_SUCCESS;


/**
 * 申请一个详情解析任务
 * Created by zhangcheng
 */
@Slf4j
@Component
public class DetalisTaskService extends BasicApiService {


    @Autowired
    RedisServiceImpl redisServiceImpl;

    @Override
    public ApiResponse execute(ApiRequest request) {
        String machineId = getApiContext().getMachineId();
        DetailsRule task = null;

        //客户端机器在线
        if (redisServiceImpl.hexists(MACHINE_QUEUE_PREFIX, machineId)) {
            task = JSON.parseObject(redisServiceImpl.hget(R_DETAILS_TASK, redisServiceImpl.lpop(R_DETAILS_TASK_KEY)), DetailsRule.class);

        }
        DetalisTaskResponse response = new DetalisTaskResponse();
        response.setOperateCodeHolder(DETALIS_TASK_SUCCESS);
        response.setTask(task);
        return response;
    }
}
