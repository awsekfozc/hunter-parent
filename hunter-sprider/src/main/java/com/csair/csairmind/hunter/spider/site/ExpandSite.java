package com.csair.csairmind.hunter.spider.site;

import us.codecraft.webmagic.Site;

/**
 * Created by zhangcheng
 */
public class ExpandSite extends Site {

    public static Site me(){
        return new ExpandSite()
                .setSleepTime(1000)
                .setTimeOut(3000)
                .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
    }
}
