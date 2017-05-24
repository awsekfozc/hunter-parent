package com.csair.csairmind.hunter.spider.factory;

import com.csair.csairmind.hunter.spider.distinct.Distinct;
import com.csair.csairmind.hunter.spider.distinct.UrlDistinct;

/**
 * Created by zhangcheng
 * 去重插件工厂
 */
public class DistinctFactory {

    public static Distinct getInstance(Integer type){
        Distinct distinct = null;
        if(type == 1)
            distinct = new UrlDistinct();
        return distinct;
    }
}
