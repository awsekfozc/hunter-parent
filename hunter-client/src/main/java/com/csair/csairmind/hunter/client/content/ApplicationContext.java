package com.csair.csairmind.hunter.client.content;


import com.csair.csairmind.hunter.common.inf.MgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangcheng
 */
@Component
public interface ApplicationContext {
    /**
     * 启动服务
     */
    public void start();
    /**
     * 退出服务
     */
    public  void stop();

}
