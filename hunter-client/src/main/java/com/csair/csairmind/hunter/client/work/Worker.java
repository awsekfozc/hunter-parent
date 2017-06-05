package com.csair.csairmind.hunter.client.work;

/**
 * 线程接口
 * Created by zhangcheng
 */
public interface Worker {
    public  void start();
    public  void stop();
    public void sleep(int millionSeconds) throws InterruptedException;
}
