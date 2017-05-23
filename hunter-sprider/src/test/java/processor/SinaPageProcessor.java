package processor;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by zhangcheng on 17/4/18.
 */
public class SinaPageProcessor implements PageProcessor {

    private Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36")
            .setRetryTimes(3)
            .setSleepTime(1000);

    public void process(Page page) {
        System.out.println("pageinfo:");
        List<String> urls = page.getHtml().xpath("//div[@class=\"r-info r-info2\"]").links().all();
        for (String url : urls)
            System.out.println(url);
        page.addTargetRequests(urls);
    }

    public SinaPageProcessor() throws Exception {
        //this.site = WeiBoMSelenium.click(this.site, "18007303287", "qwertsekfo1");
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws Exception {
        Spider.create(new SinaPageProcessor()).addUrl(String.format("http://search.sina.com.cn/?q=%s&range=all&c=news&sort=time",URLEncoder.encode("南航","GBK"))).run();
    }
}
