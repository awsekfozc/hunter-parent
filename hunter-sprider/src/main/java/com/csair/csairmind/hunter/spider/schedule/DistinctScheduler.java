package com.csair.csairmind.hunter.spider.schedule;

import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * Created by zhangcheng on 2017/5/24 0024.
 */
public interface DistinctScheduler extends Scheduler {
   void pushDistinctQueue(String info);

   void pushTask(String taskStr);
}
