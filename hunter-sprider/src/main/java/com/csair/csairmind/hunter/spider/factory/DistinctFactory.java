package com.csair.csairmind.hunter.spider.factory;

import com.csair.csairmind.hunter.spider.distinct.ContentDistinct;
import com.csair.csairmind.hunter.spider.distinct.Distinct;
import com.csair.csairmind.hunter.spider.distinct.UrlDistinct;

import static com.csair.csairmind.hunter.common.enums.SpriderEnums.CONTENT_DISTINCT;
import static com.csair.csairmind.hunter.common.enums.SpriderEnums.URL_DISTINCT;

/**
 * Created by zhangcheng
 * 去重插件工厂
 */
public class DistinctFactory {

    public static Distinct getInstance(Integer type) {
        Distinct distinct = null;
        if (type == URL_DISTINCT.getCode())//url去重
            distinct = new UrlDistinct();
        else if (type == CONTENT_DISTINCT.getCode())//content去重
            distinct = new ContentDistinct();
        return distinct;
    }
}
