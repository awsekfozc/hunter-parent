package com.csair.csairmind.hunter.spider.processor.currency;

import com.csair.csairmind.hunter.spider.site.ExpandSite;
import lombok.Data;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by zhangcheng
 */
@Data
public class DetailsProcessor implements PageProcessor{

    private String title_extract_rule;
    private String date_extract_rule;
    private String source_extract_rule;
    private String content_extract_rule;
    private Site site = ExpandSite.me();

    @Override
    public void process(Page page) {

    }
}
