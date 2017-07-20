package com.csair.csairmind.hunter.spider.processor.currency;

import com.csair.csairmind.hunter.common.constant.DataConstants;
import com.csair.csairmind.hunter.common.vo.DetailsRule;
import com.csair.csairmind.hunter.common.vo.Rule;
import com.csair.csairmind.hunter.spider.site.ExpandSite;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangcheng
 * 详情解析任务处理器---列表详情类型
 */
@Data
public class DetailsListProcessor implements HunterPageProcessor {

    private DetailsRule detailsRule;

    private Site site = ExpandSite.me();

    private List<Map<String,Object>> dataList = new ArrayList<>();

    @Override
    public void process(Page page,Rule rule) {
        this.setDetailsRule((DetailsRule)rule);
        List<String> titles = page.getHtml().xpath(detailsRule.getTitle_extract_rule()).all();
        List<String> dates = page.getHtml().xpath(detailsRule.getDate_extract_rule()).all();
        List<String> contents = page.getHtml().xpath(detailsRule.getContent_extract_rule()).all();
        List<String> urls = page.getHtml().xpath(detailsRule.getSource_extract_rule()).all();
        if (CollectionUtils.isNotEmpty(titles)) {
            Map<String,Object> data = new HashMap<>();
            for (int i = 0; i < titles.size(); i++) {
                data.put(DataConstants.TASK_ID,rule.getTask_id());
                data.put(DataConstants.TITLE,titles.get(i));
                data.put(DataConstants.DATE_TIME,dates.get(i));
                data.put(DataConstants.CONTENT,contents.get(i));
                data.put(DataConstants.DATA_SOURCE,detailsRule.getData_source());
                if (StringUtils.isBlank(detailsRule.getSource_extract_rule()))
                    data.put(DataConstants.DATA_URL, detailsRule.getUrl());
                else
                    data.put(DataConstants.DATA_URL, urls.get(i));
                dataList.add(data);
            }
        }
        page.putField(DataConstants.DATA_LIEST,dataList);
    }

}