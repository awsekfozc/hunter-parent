package com.csair.csairmind.hunter.spider.processor.currency;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * Created by zhangcheng
 * 拓展页面解析接口
 */
public interface HunterPageProcessor{

    void process(Page var1,Object rule);

    Site getSite();
}
