package processor;


import selenium.WeiBoMSelenium;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by zhangcheng on 17/4/18.
 */
public class WeiboMPageProcessor implements PageProcessor {

    private Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36")
            .setRetryTimes(3)
            .setSleepTime(1000);
//            .addHeader("Accept", "application/json, text/plain, */*")
//            .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
//            .addHeader("Accept-Encoding", "gzip, deflate, sdch")
//            .addHeader("Connection", "keep-alive")
//            .addHeader("Cookie", "_T_WM=b9a79c6fcb51ba378119718fbf644bf2; ALF=1498027387; SCF=AkWMTXlt24R4Huksml1QkFfmkTnE4Ag0ZxMY1z4BcsvFrqtRLZcO_8heA1-UBRe8CIxNRMsZpbpix2GUOXQHJ9g.; SUB=_2A250JvgsDeRhGeNL7VET9CfKzT-IHXVX6JhkrDV6PUJbktBeLWHhkW1gsnlhdTt1j9Aj8XQ-Eqv3fow61A..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWsYlU1OflQ6r8kw5BMGwxR5JpX5o2p5NHD95QfSKq0eoB4Soq0Ws4DqcjMi--NiK.Xi-2Ri--ciKnRi-zNSoMNSKn7SKMRS7tt; SUHB=0M21sJ9UuD3DvJ; SSOLoginState=1495435388; H5_INDEX=0_all; H5_INDEX_TITLE=%E7%94%A8%E6%88%B76775805787; M_WEIBOCN_PARAMS=oid%3D4108705001877677%26sourceType%3Dweixin%26featurecode%3D20000180%26luicode%3D20000061%26lfid%3D4108705001877677%26uicode%3D20000061%26fid%3D4108705001877677")
//            .addHeader("Host", "m.weibo.cn")
//            .addHeader("Referer", "https://m.weibo.cn/status/4108705001877677")
//            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36")
//            .addHeader("X-Requested-With", "XMLHttpRequest");

    public void process(Page page) {
        System.out.println("pageinfo:");
        List<String> urls = page.getHtml().xpath("//div[@class=\"r-info r-info2\"]").links().all();
        for (String url : urls)
            System.out.println(url);
//        System.out.println(page.getRawText());
    }

    public WeiboMPageProcessor() throws Exception {
        //this.site = WeiBoMSelenium.click(this.site, "18007303287", "qwertsekfo1");
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws Exception {
        Spider.create(new WeiboMPageProcessor()).addUrl(String.format("http://search.sina.com.cn/?q=%s&range=all&c=news&sort=time",URLEncoder.encode("南航","GBK"))).run();
    }
}
