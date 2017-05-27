package com.csair.csairmind.hunter.spider.processor.currency;

import com.csair.csairmind.hunter.common.constant.DataConstants;
import com.csair.csairmind.hunter.common.enums.SpriderEnums;
import com.csair.csairmind.hunter.common.util.ContentUtils;
import com.csair.csairmind.hunter.common.vo.DetailsTask;
import com.csair.csairmind.hunter.spider.site.ExpandSite;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangcheng
 * 详情解析任务处理器---列表详情类型
 */
@Data
public class DetailsListProcessor implements PageProcessor {

    private DetailsTask task;

    private Site site = ExpandSite.me();

    private List<Map<String,Object>> dataList = new ArrayList<>();

    public DetailsListProcessor(DetailsTask task) {
        this.task = task;
    }


    @Override
    public void process(Page page) {
        List<String> titles = page.getHtml().xpath(task.getTitle_extract_rule()).all();
        List<String> dates = page.getHtml().xpath(task.getDate_extract_rule()).all();
        List<String> contents = page.getHtml().xpath(task.getContent_extract_rule()).all();
        List<String> urls = page.getHtml().xpath(task.getSource_extract_rule()).all();
        if (CollectionUtils.isNotEmpty(titles)) {
            Map<String,Object> data = new HashMap<>();
            for (int i = 0; i < titles.size(); i++) {
                data.put(DataConstants.TITLE,titles.get(i));
                data.put(DataConstants.DATE_TIME,dates.get(i));
                data.put(DataConstants.CONTENT,contents.get(i));
                data.put(DataConstants.DATA_SOURCE,task.getData_source());
                if (StringUtils.isBlank(task.getSource_extract_rule()))
                    data.put(DataConstants.DATA_URL, task.getUrl());
                else
                    data.put(DataConstants.DATA_URL, urls.get(i));
                dataList.add(data);
            }
        }
        page.putField(DataConstants.DATA_LIEST,dataList);
    }

}