package com.csair.csairmind.hunter.client.work;

import com.csair.csairmind.hunter.client.api.DefaultApiClient;
import com.csair.csairmind.hunter.client.content.DefaultApplicationContext;
import static com.csair.csairmind.hunter.common.enums.OperateCodeHolder.RESOURCE_TASK_SUCCESS;

import com.csair.csairmind.hunter.common.config.RedisConfig;
import com.csair.csairmind.hunter.common.config.RedisConfigVo;
import com.csair.csairmind.hunter.common.request.OperateResult;
import com.csair.csairmind.hunter.common.request.ResourceTaskRequest;
import com.csair.csairmind.hunter.common.response.ApiResponse;
import com.csair.csairmind.hunter.common.response.ResourceTaskResponse;
import com.csair.csairmind.hunter.common.vo.ResourceTask;
import com.csair.csairmind.hunter.spider.ExpandSpider;
import com.csair.csairmind.hunter.spider.factory.DistinctFactory;
import com.csair.csairmind.hunter.spider.processor.currency.ResourcesProcessor;
import com.csair.csairmind.hunter.spider.schedule.ResourceTaskScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;


/**
 * Created by zhangcheng
 */
@Slf4j
@Component
public class ResourceWork extends BaseThread {

    @Autowired
    RedisConfigVo redisConfigVo;

    public ResourceWork(){
        super.initClient();
    }

    @Override
    public void doing() {
        try{
            ResourceTaskRequest request = new ResourceTaskRequest();
            OperateResult result = defaultApiClient.execute(request);
            if (!result.getOperateCodeHolder().equals(RESOURCE_TASK_SUCCESS)) {
                takeARest(1000);
            }else{
                ApiResponse response = result.getResponse();
                ResourceTaskResponse rsp = (ResourceTaskResponse) response;
                ResourceTask task = rsp.getTask();
                if (task == null) {
                    log.info("无资源解析任务");
                }else{
                    log.info("开始执行资源解析任务");
                    ExpandSpider.create(new ResourcesProcessor(task), new JedisPool(redisConfigVo.getHostName()))
                            .setScheduler(new ResourceTaskScheduler())
                            .setStartRequest(task.getUrl())
                            .setDistinct(DistinctFactory.getInstance(task.getDistinct_type()))
                            .run();
                }
            }
        }catch (Exception e){
            log.error("申请任务失败，调用接口出错",e);
        }
    }
}
