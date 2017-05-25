package com.csair.csairmind.hunter.common.vo;

import lombok.Data;

/**
 * Created by zhangcheng on 2017/5/25 0025.
 */
@Data
public class DetailsTask {

    //标题抽取规则
    private String title_extract_rule;

    //时间抽取规则
    private String date_extract_rule;

    //来源抽取规则
    private String source_extract_rule;

    //内容抽取规则
    private String content_extract_rule;

    //数据来源
    private String data_source;
}
