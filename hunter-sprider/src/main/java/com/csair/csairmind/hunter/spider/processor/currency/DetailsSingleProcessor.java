package com.csair.csairmind.hunter.spider.processor.currency;

import com.csair.csairmind.hunter.common.constant.DataConstants;
import com.csair.csairmind.hunter.common.util.ContentUtils;
import com.csair.csairmind.hunter.common.vo.DetailsRule;
import com.csair.csairmind.hunter.common.vo.Rule;
import com.csair.csairmind.hunter.spider.site.ExpandSite;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * Created by zhangcheng
 * 详情解析任务处理器---单条详情类型
 */
@Data
public class DetailsSingleProcessor implements HunterPageProcessor {

    private DetailsRule rule;

    private Site site = ExpandSite.me();

    @Override
    public void process(Page page,Rule rule) {
        this.setRule((DetailsRule)rule);
        page.putField(DataConstants.TITLE, page.getHtml().xpath(this.rule.getTitle_extract_rule()).get());
        page.putField(DataConstants.DATE_TIME, page.getHtml().xpath(this.rule.getDate_extract_rule()).get());
        page.putField(DataConstants.CONTENT, ContentUtils.removeUrlContent(page.getHtml().xpath(this.rule.getContent_extract_rule()).get()));
        page.putField(DataConstants.DATA_SOURCE, this.rule.getData_source());
        if (StringUtils.isBlank(this.rule.getSource_extract_rule()))
            page.putField(DataConstants.DATA_URL, this.rule.getUrl());
        else
            page.putField(DataConstants.DATA_URL, page.getHtml().xpath(this.rule.getSource_extract_rule()).get());
    }
}