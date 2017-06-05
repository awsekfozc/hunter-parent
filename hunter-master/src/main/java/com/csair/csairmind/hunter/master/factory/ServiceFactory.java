package com.csair.csairmind.hunter.master.factory;


import com.csair.csairmind.hunter.common.ApiHolder;
import com.csair.csairmind.hunter.common.spring.ApplicationContextProvider;
import com.csair.csairmind.hunter.master.service.BeatService;
import com.csair.csairmind.hunter.master.service.IApiService;
import com.csair.csairmind.hunter.master.service.RegisterService;
import com.csair.csairmind.hunter.master.service.ResourceTaskService;
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
        }
        //else if(ApiHolder.UPLOAD_URL.equalsIgnoreCase(apiName)){
//            //上传URL
//            service= new UploadResourceService();
//        }else if(ApiHolder.TASK_GET_STATUS.equalsIgnoreCase(apiName)){
//            //获取任务状态
//            service= new GetTaskStatusService();
//        }else if(ApiHolder.REPORT_LOG.equalsIgnoreCase(apiName)){
//            //上传日志
//            service= new ReportLogService();
//        }else if(ApiHolder.REPORT_REPEAT.equalsIgnoreCase(apiName)){
//            //上报重复
//            service= new ReportRepeatService();
//        }else if(ApiHolder.UPLOAD_TASK_STATUS.equalsIgnoreCase(apiName)){
//            //上报重复
//            service= new UploadTaskStatusService();
//        }
        return service;
    }
}
