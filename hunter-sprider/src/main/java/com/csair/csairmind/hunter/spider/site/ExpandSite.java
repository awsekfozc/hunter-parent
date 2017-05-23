package com.csair.csairmind.hunter.spider.site;

import us.codecraft.webmagic.Site;

/**
 * Created by zhangcheng on 2017/5/22 0022.
 */
public class ExpandSite extends Site {

    public static Site me(){
        return new ExpandSite().setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
    }
}
