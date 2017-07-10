package processor;


import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangcheng on 17/4/18.
 */
public class New163PageProcessor implements PageProcessor {

    private Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36")
            .setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("Accept","*/*");

    String rule = "http://news.163.com/(\\d+)/(\\d+)/(\\d+)/(\\w+).html";
    public void process(Page page) {
//        System.out.println("pageinfo:"+ page.getRawText());
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(page.getRawText());
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
//        List<String> urls = page.getRawText().regex(rule).links().all();
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
        Spider.create(new New163PageProcessor()).addUrl(String.format("http://temp.163.com/special/00804KVA/cm_guonei.js?callback=data_callback")).run();
    }
}
