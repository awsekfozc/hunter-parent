package com.csair.csairmind.hunter.common.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangcheng
 * 资源解析任务实体类
 */
@Data
@ToString
public class ResourceRule implements Rule {

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
    private Integer max_page_num;

    //当前页数
    private Integer now_page_mun;

    //分页表达式
    private String paging_reg;

    /***
     * 以下为详情解析任务需要信息
     */

    //持久化类型
    private Integer pipeline_type = 0;

    //去重类型
    private Integer distinct_type = 1;
    //请求路径
    private String request_url;

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

    /***
     * 构造详情所需参数
     * @return
     */
    public Map<String, Object> getDetailsParam() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", request_url);
        map.put("distinct_type", distinct_type);
        map.put("title_extract_rule", title_extract_rule);
        map.put("date_extract_rule", date_extract_rule);
        map.put("source_extract_rule", source_extract_rule);
        map.put("content_extract_rule", content_extract_rule);
        map.put("data_source", data_source);
        map.put("task_type", task_type);
        return map;
    }
}
