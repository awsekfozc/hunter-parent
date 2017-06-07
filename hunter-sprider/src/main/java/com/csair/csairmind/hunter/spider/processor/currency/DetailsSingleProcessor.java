package com.csair.csairmind.hunter.spider.processor.currency;

import com.csair.csairmind.hunter.common.constant.DataConstants;
import com.csair.csairmind.hunter.common.util.ContentUtils;
import com.csair.csairmind.hunter.common.vo.DetailsRule;
import com.csair.csairmind.hunter.spider.site.ExpandSite;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by zhangcheng
 * 详情解析任务处理器---单条详情类型
 */
@Data
public class DetailsSingleProcessor implements HunterPageProcessor {

    private DetailsRule task;

    private Site site = ExpandSite.me();

    @Override
    public void process(Page page,Object rule) {
        this.setTask((DetailsRule)rule);
        page.putField(DataConstants.TITLE, page.getHtml().xpath(task.getTitle_extract_rule()));
        page.putField(DataConstants.DATE_TIME, page.getHtml().xpath(task.getDate_extract_rule()));
        page.putField(DataConstants.CONTENT, ContentUtils.removeUrlContent(page.getHtml().xpath(task.getContent_extract_rule()).get()));
        page.putField(DataConstants.DATA_SOURCE, task.getData_source());
        if (StringUtils.isBlank(task.getSource_extract_rule()))
            page.putField(DataConstants.DATA_URL, task.getUrl());
        else
            page.putField(DataConstants.DATA_URL, page.getHtml().xpath(task.getSource_extract_rule()));

    }
}