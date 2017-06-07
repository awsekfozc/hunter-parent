package com.csair.csairmind.hunter.service.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SpriderTestResponse {

    @ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "日期")
    private String date_time;

	@ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "链接")
    private String data_url;
}