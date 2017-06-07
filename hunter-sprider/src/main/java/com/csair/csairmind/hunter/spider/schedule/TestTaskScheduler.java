package com.csair.csairmind.hunter.spider.schedule;

import com.csair.csairmind.hunter.common.util.BeanToMapUtil;
import com.csair.csairmind.hunter.common.vo.DetailsRule;
import lombok.Getter;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangcheng
 * 测试爬虫任务调度器
 */
public class TestTaskScheduler implements DistinctScheduler {

    @Getter
    private List<DetailsRule> detalisTask = new ArrayList<DetailsRule>();

    @Override
    public void pushDistinctQueue(String info) {

    }

    @Override
    public void pushTask(String taskStr) {

    }

    @Override
    public void push(Request request, Task task) {
        DetailsRule detailsRule = new DetailsRule();
        detalisTask.add(BeanToMapUtil.mapToBean(request.getExtras(), detailsRule));
    }

    @Override
    public Request poll(Task task) {
        return null;
    }
}
