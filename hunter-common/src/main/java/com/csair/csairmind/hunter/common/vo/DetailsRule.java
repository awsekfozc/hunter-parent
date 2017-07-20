package com.csair.csairmind.hunter.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangcheng
 */
@Data
public class DetailsRule implements Rule {

    //任务编号
    private String task_id;

    //持久化类型
    private Integer pipeline_type = 0;

    //去重类型
    private Integer distinct_type = 1;

    //请求路径
    private String url;

    //标题抽取规则
    private String title_extract_rule;

    //时间抽取规则
    private String date_extract_rule;

    //数据连接抽取规则
    private String source_extract_rule;

    //内容抽取规则
    private String content_extract_rule;

    //数据来源
    private String data_source;

    //任务类型
    private Integer task_type;

    //是否启用代理
    private Integer is_proxy = 0;
}
