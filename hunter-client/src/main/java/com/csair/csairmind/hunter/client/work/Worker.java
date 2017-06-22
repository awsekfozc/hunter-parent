package com.csair.csairmind.hunter.client.work;

/**
 * 线程接口
 * Created by zhangcheng
 */
public interface Worker {
    void start();

    void stop();

    void sleep(int millionSeconds) throws InterruptedException;
}
