package com.csair.csairmind.hunter.spider.processor.currency;

import com.csair.csairmind.hunter.common.vo.DetailsTask;
import com.csair.csairmind.hunter.spider.site.ExpandSite;
import lombok.Data;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by zhangcheng
 * 详情解析任务处理器
 */
@Data
public class DetailsProcessor implements PageProcessor{

    private DetailsTask task;

    private Site site = ExpandSite.me();

    @Override
    public void process(Page page) {

    }
}