package com.csair.csairmind.hunter.common.constant;

/**
 * Created by zhangcheng.
 */
public class SprderConstants {

    //redis键变量
    //注册进来的机器 hash
    public static final String MACHINE_QUEUE_PREFIX = "machine_queue";

    //机器现在在处理的内容 hash
    public static final String MACHINE_RUN = "machine_run";


    //任务列表hash(由OA写入或者手工创建任务)
    public static final String TASK_QUEUE = "task_queue";
    //待处理任务 hash
    public static final String TASK_WAIT_QUEUE = "task_wait_queue";
    //正在解释任务 hash
    public static final String TASK_RUNING_QUEUE = "task_runing_queue";


    //待爬取内容任务 加上任务ID作为key的list
    public static final String WAIT_CRAWL_QUEUE = "wait_crawl_queue_";
    //待爬取内容URl去重
    public static final String CRAWL_REAPET = "crawl_reapet_";

    //OA控制任务停止list
    public static final String STOP_TASK = "stop_task";

    //爬虫日志
    public static final String SPRIDER_LOG = "sprider_log";

    //作业任务类型
    public static final int TASK_EXPLAIN = 0; //解释资源
    public static final int TASK_CRAWL = 1;   //爬取数据

   //运行锁
    public static final String RUN_ROCK = "run_rock";

    //作业任务类型
    public static final String TASK_CLASS_GE = "1001"; //通用型爬虫
    public static final String TASK_CLASS_CM = "1002";   //定制型爬虫

}
