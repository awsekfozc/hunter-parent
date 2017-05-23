package processor;


import com.alibaba.fastjson.JSON;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by zhangcheng on 17/4/18.
 */
public class New163PageProcessor implements PageProcessor {

    private Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36")
            .setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("Accept-Language","zh-CN,zh;q=0.8")
            .addHeader("Accept-Encoding","gzip, deflate, sdch");

    public void process(Page page) {
        System.out.println("pageinfo:"+ page.getRawText());
//        List<String> urls = page.getHtml().jsonPath("//div[@class=\"r-info r-info2\"]").links().all();
//        for (String url : urls)
//            System.out.println(url);
//        page.addTargetRequests(urls);
    }

    public New163PageProcessor() throws Exception {
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws Exception {
        Spider.create(new New163PageProcessor()).addUrl(String.format("http://news.163.com/domestic/")).run();
    }
}
