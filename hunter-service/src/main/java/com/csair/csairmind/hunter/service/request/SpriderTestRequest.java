package com.csair.csairmind.hunter.service.request;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by zhangcheng
 * 测试爬虫任务请求
 */
@Data
@ApiModel
public class SpriderTestRequest {

	//地址
	@NotBlank(message = "参数[url]不能为空")
	@ApiModelProperty(value = "网站地址", required = true)
	private String url;

	//详情地址Xpath
	@ApiModelProperty(value = "详情地址Xpath", required = false)
	private String details_url_xpath;

	//详情地址正则表达式
	@ApiModelProperty(value = "详情地址正则表达式", required = false)
	private String details_url_reg;

	//详情地址Jpath
	@ApiModelProperty(value = "详情地址Jpath", required = false)
	private String details_url_jpath;

	//标题在网页上的位置
	@NotBlank(message = "参数[title_extract_rule]不能为空")
	@ApiModelProperty(value = "标题在网页上的位置", required = true)
	private String title_extract_rule;

	//日期在网页上的位置
	@NotBlank(message = "参数[date_extract_rule]不能为空")
	@ApiModelProperty(value = "日期在网页上的位置", required = true)
	private String date_extract_rule;

	//来源URL在网页上的位置
	@ApiModelProperty(value = "来源URL在网页上的位置", required = false)
	private String source_extract_rule;

	//内容在网页上的位置
	@NotBlank(message = "参数[content_extract_rule]不能为空")
	@ApiModelProperty(value = "内容在网页上的位置", required = true)
	private String content_extract_rule;

	//任务类型
	@ApiModelProperty(value = "任务类型", required = true)
	private Integer task_type;

	//搜索关键字
	@ApiModelProperty(value = "搜索关键字", required = false)
	private String search_wrods;

}
