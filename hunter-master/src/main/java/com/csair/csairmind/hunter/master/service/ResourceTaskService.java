package com.csair.csairmind.hunter.master.service;

import com.alibaba.fastjson.JSON;
import static com.csair.csairmind.hunter.common.constant.SprderConstants.MACHINE_QUEUE_PREFIX;
import static com.csair.csairmind.hunter.common.constant.SprderConstants.R_RESOURCE_TASK;
import static com.csair.csairmind.hunter.common.enums.OperateCodeHolder.RESOURCE_TASK_SUCCESS;
import com.csair.csairmind.hunter.common.plug.RedisServiceImpl;
import com.csair.csairmind.hunter.common.request.ApiRequest;
import com.csair.csairmind.hunter.common.response.ApiResponse;
import com.csair.csairmind.hunter.common.response.ResourceTaskResponse;
import com.csair.csairmind.hunter.common.vo.ResourceTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 申请一个资源解析任务
 * Created by zhangcheng
 */
@Slf4j
@Component
public class ResourceTaskService extends BasicApiService {


    @Autowired
    RedisServiceImpl redisServiceImpl;

    @Override
    public ApiResponse execute(ApiRequest request) {
        String machineId = getApiContext().getMachineId();
        ResourceTask task = null;

        //客户端机器在线
        if (redisServiceImpl.hexists(MACHINE_QUEUE_PREFIX, machineId)) {
            task = JSON.parseObject(redisServiceImpl.lpop(R_RESOURCE_TASK), ResourceTask.class);
        }
        ResourceTaskResponse response = new ResourceTaskResponse();
        response.setOperateCodeHolder(RESOURCE_TASK_SUCCESS);
        response.setTask(task);
        return response;
    }
}
