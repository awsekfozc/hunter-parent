package com.csair.csairmind.hunter.spider.increment;

import com.csair.csairmind.hunter.common.vo.Rule;

/**
 * Created by fate
 * 采集任务增量接口
 */
public interface TaskIncrement {
    void put(Rule rule);
}
