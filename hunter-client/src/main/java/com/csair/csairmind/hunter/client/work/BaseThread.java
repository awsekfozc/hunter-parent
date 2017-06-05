package com.csair.csairmind.hunter.client.work;

import com.csair.csairmind.hunter.client.api.DefaultApiClient;
import com.csair.csairmind.hunter.client.content.DefaultApplicationContext;
import com.csair.csairmind.hunter.common.config.AppValidateInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 线程基类，对thread进行封装
 * Created by zhangcheng
 */
@Slf4j
public abstract class BaseThread implements Worker {

    @Autowired
    AppValidateInfo appValidateInfo;

    @Autowired
    DefaultApiClient defaultApiClient;

    protected WorkingThread workingThread=null;

    private boolean running;

    public boolean isRunning() {
        return running;
    }


    @Override
    public void start() {
        startThread();
    }

    @Override
    public void sleep(int millionSeconds) throws InterruptedException {
        if(workingThread!=null){
            workingThread.sleep(500);
        }
    }

    @Override
    public void stop() {
        stopThread();
    }

    private  void stopThread(){
        running=false;
        if(workingThread==null) return;
        try{
            Thread.sleep(100);
            workingThread.interrupt();
            workingThread.join();
            workingThread.sleep(500);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void initClient() {
        String appKey = appValidateInfo.getAppKey();
        String appSecret = appValidateInfo.getAppSecret();
        String machineId = DefaultApplicationContext.MACHINEID;
        defaultApiClient.appKey = appKey;
        defaultApiClient.appSecret = appSecret;
        defaultApiClient.machineId = machineId;
    }

    private  void startThread(){
        if(workingThread!=null){
            stopThread();
        }
        workingThread=new WorkingThread();
        running=true;
        workingThread.start();
    }

    /**
     * 执行方法
     */
    public abstract  void doing();

    class  WorkingThread extends  Thread{
        public void run() {
            int sleeptime = appValidateInfo.getApplyTaskTime();
            running=true;
            while (running){
                try {
                    doing();
                    Thread.sleep(sleeptime);
                } catch (Exception ex) {
                    log.error(String.format("线程[%s]出现异常!",Thread.currentThread().getName()),ex);
                    try {
                        Thread.sleep(appValidateInfo.getException_wait_mill_seconds());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {

                }
            }
            log.info(String.format("线程[%s]处理退出!",Thread.currentThread().getName()));
        }
    }

    /**
     * 休息一下
     * @param millionSeconds
     */
    protected void takeARest(int millionSeconds){
        try {
            Thread.sleep(millionSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
