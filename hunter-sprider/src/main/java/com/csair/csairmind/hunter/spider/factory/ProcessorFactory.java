package com.csair.csairmind.hunter.spider.factory;

import com.csair.csairmind.hunter.common.vo.DetailsRule;
import com.csair.csairmind.hunter.common.vo.ResourceRule;
import com.csair.csairmind.hunter.common.vo.Rule;
import com.csair.csairmind.hunter.spider.processor.currency.DetailsListProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.DetailsSingleProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.HunterPageProcessor;
import com.csair.csairmind.hunter.spider.processor.currency.ResourcesProcessor;

import static com.csair.csairmind.hunter.common.enums.SpriderEnums.DETAILS_LIST_PRO;

/**
 * Created by fate
 * 页面处理器工厂-根据爬虫规则生成
 */
public class ProcessorFactory {

    public static HunterPageProcessor initProcessor(Rule rule) {
        HunterPageProcessor processor = null;
        if (rule instanceof ResourceRule) {
            processor = new ResourcesProcessor();
        } else if (rule instanceof DetailsRule) {
            int details_task_type = ((DetailsRule) rule).getTask_type();
            if (details_task_type == DETAILS_LIST_PRO.getCode()) {//如果是列表详情解析
                processor = new DetailsListProcessor();
            } else {
                processor = new DetailsSingleProcessor();
            }
        }
        return processor;
    }
}
