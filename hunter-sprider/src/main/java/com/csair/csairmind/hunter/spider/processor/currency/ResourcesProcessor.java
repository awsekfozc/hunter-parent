package com.csair.csairmind.hunter.spider.processor.currency;

import com.csair.csairmind.hunter.spider.site.ExpandSite;
import com.csair.csairmind.hunter.spider.vo.ResourceTask;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * Created by zhangcheng
 * 资源解析任务处理
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
        List<String> urls ;
        if (StringUtils.isNotBlank(task.getDetails_url_reg())) {
            urls = getDetailsUrlsReg(page.getHtml());
            page.addTargetRequests(urls);
            log.info("正则表达式解析资源，新增详情链接 {} 个",urls.size());
        } else {
            urls = getDetailsUrlsXpath(page.getHtml());
            page.addTargetRequests(urls);
            log.info("Xpath解析资源，新增详情链接 {} 个",urls.size());
        }
    }

    /***
     * 获取详情URL，Xpath形式
     * @param html
     * @return
     */
    public List<String> getDetailsUrlsXpath(Html html) {
        return html.xpath(task.getDetails_url_xpath()).links().all();
    }

    /***
     * 获取详情URL，正则表达式形式
     * @param html
     * @return
     */
    public List<String> getDetailsUrlsReg(Html html) {
        return html.regex(task.getDetails_url_reg()).links().all();
    }

    /***
     * 获取详情URL，正则表达式形式
     * @param html
     * @return
     */
    public List<String> getDetailsUrlsJpath(Html html) {
//        html.jsonPath(task.getDetails_url_reg()).
        return html.regex(task.getDetails_url_reg()).links().all();
    }
}
