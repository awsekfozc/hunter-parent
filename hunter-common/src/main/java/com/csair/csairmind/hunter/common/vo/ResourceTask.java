package com.csair.csairmind.hunter.common.vo;

import lombok.Data;
import lombok.ToString;

/**
 * Created by zhangcheng on 2017/5/23 0023.
 * 资源解析任务实体类
 */
@Data
@ToString
public class ResourceTask {
    //详情URL表达式
    private String details_url_reg;

    //详情URL-Xpath
    private String details_url_xpath;

    //入口URL
    private String url;

    //详情URL-Jpath
    private String details_url_jpath;

    //任务编号
    private String task_id;

    //最大页数
    private Integer maxPageSize;

    //当前页数
    private Integer nowPageSize;

    //分页表达式
    private String paging_reg;

    //去重类型
    private Integer distinct_type = 1;
}
