package com.csair.csairmind.hunter.spider.factory;

import com.csair.csairmind.hunter.common.vo.Rule;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by fate
 * 持久化示例工厂
 */
public class PipelineFactory {

    public static Pipeline initPipeline(Rule rule) {
        Pipeline pipeline = null;
        switch (rule.getPipeline_type()) {
            case 1:
                pipeline = null;
                break;
            case 0:
                pipeline = new ConsolePipeline();
                break;
        }
        return pipeline;
    }

}
