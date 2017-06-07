package com.csair.csairmind.hunter.spider.pipeline;

import com.csair.csairmind.hunter.common.util.BeanToMapUtil;
import com.csair.csairmind.hunter.common.vo.SingleDataVo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangcheng
 */
@Slf4j
public class SpriderTestPipeline implements Pipeline {

    @Getter
    private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

    @Override
    public void process(ResultItems resultItems, Task task) {
        dataList.add(resultItems.getAll());
    }
}
