package com.csair.csairmind.hunter.spider.processor.currency;

import com.csair.csairmind.hunter.common.constant.DataConstants;
import com.csair.csairmind.hunter.common.enums.SpriderEnums;
import com.csair.csairmind.hunter.common.util.ContentUtils;
import com.csair.csairmind.hunter.common.vo.DetailsTask;
import com.csair.csairmind.hunter.spider.site.ExpandSite;
import lombok.Data;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by zhangcheng
 * 详情解析任务处理器---单条详情类型
 */
@Data
public class DetailsSingleProcessor implements PageProcessor {

    private DetailsTask task;

    private Site site = ExpandSite.me();

    public DetailsSingleProcessor(DetailsTask task) {
        this.task = task;
    }

    @Override
    public void process(Page page) {
        page.putField(DataConstants.TITLE, page.getHtml().xpath(task.getTitle_extract_rule()));
        page.putField(DataConstants.DATE_TIME, page.getHtml().xpath(task.getDate_extract_rule()));
        page.putField(DataConstants.CONTENT, ContentUtils.removeUrlContent(page.getHtml().xpath(task.getContent_extract_rule()).get()));
        page.putField(DataConstants.DATA_SOURCE, task.getData_source());
        if (task.getTask_type() == SpriderEnums.DETAILS_ALL_PRO.getCode())
            page.putField(DataConstants.DATA_URL, task.getRequest_url());
        else
            page.putField(DataConstants.DATA_URL, page.getHtml().xpath(task.getSource_extract_rule()));

    }

}