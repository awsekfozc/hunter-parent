package com.csair.csairmind.hunter.client.work;


import com.csair.csairmind.hunter.client.api.DefaultApiClient;
import com.csair.csairmind.hunter.client.content.DefaultApplicationContext;
import com.csair.csairmind.hunter.client.service.WrapService;
import com.csair.csairmind.hunter.common.config.AppValidateInfo;
import com.csair.csairmind.hunter.common.enums.OperateCodeHolder;
import com.csair.csairmind.hunter.common.request.BeatRequest;
import com.csair.csairmind.hunter.common.request.OperateResult;
import com.csair.csairmind.hunter.common.response.ApiResponse;
import com.csair.csairmind.hunter.common.response.BeatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangcheng
 * 心跳线程
 */
@Slf4j
@Component
public class HeartWorker extends Thread {

    @Autowired
    AppValidateInfo appValidateInfo;

    @Autowired
    DefaultApiClient defaultApiClient;

    @Autowired
    WrapService service;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private boolean running;
    private int errorTryCount = 0;
    //重试上线次数
    private int MAX_ERROR_TRY_COUNT = 3;

    public void run() {
        running = true;
        while (running) {
            try {
                String appKey = appValidateInfo.getAppKey();
                String appSecret = appValidateInfo.getAppSecret();
                String machineId = DefaultApplicationContext.MACHINEID;
                defaultApiClient.appKey = appKey;
                defaultApiClient.appSecret = appSecret;
                defaultApiClient.machineId = machineId;

                BeatRequest request = new BeatRequest();
                log.info("向master发送心跳");
                OperateResult result = defaultApiClient.execute(request);
                if (result.getOperateCodeHolder().equals(OperateCodeHolder.BEAT_SUCCESS)) {
                    ApiResponse response = result.getResponse();
                    BeatResponse rsp = (BeatResponse) response;
                    if (rsp.isOk()) {
                        log.info("心跳正常....");
                    } else {
                        log.info("心跳异常....");
                        errorTryCount++;
                    }
                } else {
                    log.info("心跳异常....");
                    errorTryCount++;
                }
            } catch (Exception ex) {
                errorTryCount++;
                log.error("心跳异常!", ex);
                try {
                    Thread.sleep(appValidateInfo.getException_wait_mill_seconds());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                if (errorTryCount > MAX_ERROR_TRY_COUNT) {
                    try {
                        if (service.register()) {
                            errorTryCount = 0;
                            log.info("重新注册上线成功！");
                        } else {
                            log.info("重新注册上线失败！");
                        }
                    } catch (Exception eee) {
                        log.error("重新注册异常!", eee);
                    }
                }
            }
            try {
                Thread.sleep(appValidateInfo.getHeartBeatTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        log.info("任务处理线程退出!");
    }
}
