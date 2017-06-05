package com.csair.csairmind.hunter.master.factory;


import com.csair.csairmind.hunter.common.ApiHolder;
import com.csair.csairmind.hunter.common.spring.ApplicationContextProvider;
import com.csair.csairmind.hunter.master.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangcheng
 */
public class ServiceFactory {

    public static IApiService getService(String apiName) {
        IApiService service = null;
        if (ApiHolder.BEAT.equalsIgnoreCase(apiName)) {
            //心跳
            service = ApplicationContextProvider.getBean("beatService", BeatService.class);
        } else if (ApiHolder.REGISTER.equalsIgnoreCase(apiName)) {
            //注册
            service = ApplicationContextProvider.getBean("registerService", RegisterService.class);
        }else if(ApiHolder.RESOURCE_TASK_APPLY.equalsIgnoreCase(apiName)){
            //申请任务
            service= ApplicationContextProvider.getBean("resourceTaskService", ResourceTaskService.class);
        }else if(ApiHolder.DETALIS_TASK_APPLY.equalsIgnoreCase(apiName)){
            //申请任务
            service= ApplicationContextProvider.getBean("detalisTaskService", DetalisTaskService.class);
        }
        return service;
    }
}
