package com.csair.csairmind.hunter.spider.processor.currency;

import com.csair.csairmind.hunter.common.vo.ResourceTask;
import com.csair.csairmind.hunter.spider.site.ExpandSite;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangcheng
 * 资源解析任务处理器
 */
@Data
@Slf4j
public class ResourcesProcessor implements PageProcessor {

    private Site site = ExpandSite.me();

    private ResourceTask task;

    public ResourcesProcessor(ResourceTask task) {
        this.task = task;
    }

    public void process(Page page) {
        List<String> urls;
        try {
            if (StringUtils.isNotBlank(task.getDetails_url_reg())) {
                urls = getDetailsUrlsReg(page);
                log.info("正则表达式解析资源，发现详情链接 {} 个", urls.size());
            } else if (StringUtils.isNotBlank(task.getDetails_url_xpath())) {
                urls = getDetailsUrlsXpath(page);
                log.info("Xpath解析资源，发现详情链接 {} 个", urls.size());
            } else {
                urls = getDetailsUrlsJpath(page);
                log.info("Jpath解析资源，发现详情链接 {} 个", urls.size());
            }
            for(String url:urls){
                Request request = new Request(url);
                task.setRequest_url(url);
                request.setExtras(task.getDetailsParam());
                page.addTargetRequest(request);
            }
        } catch (Exception ex) {
            log.error("解析任务失败：{}", ex);
        }
    }

    /***
     * 获取详情URL，Xpath形式
     * @param page
     * @return
     */
    public List<String> getDetailsUrlsXpath(Page page) {
        return page.getHtml().xpath(task.getDetails_url_xpath()).links().all();
    }

    /***
     * 获取详情URL，正则表达式形式
     * @param page
     * @return
     */
    public List<String> getDetailsUrlsReg(Page page) {
        List<String> urls;
        try {
            urls = page.getHtml().regex(task.getDetails_url_reg()).links().all();
        } catch (UnsupportedOperationException ex) {
            Set<String> urlsSet = new HashSet<String>();
            Pattern pattern = Pattern.compile(task.getDetails_url_reg());
            Matcher matcher = pattern.matcher(page.getRawText());
            while (matcher.find()) {
                urlsSet.add(matcher.group());
            }
            urls = new ArrayList<String>(urlsSet);
        }
        return urls;
    }

    /***
     * 获取详情URL，Jpath形式
     * @param page
     * @return
     */
    public List<String> getDetailsUrlsJpath(Page page) {
        return page.getJson().xpath(task.getDetails_url_jpath()).links().all();
    }
}
