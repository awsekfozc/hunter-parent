package com.csair.csairmind.hunter.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * Created by zhangcheng
 * 获取节点信息请求
 */
@Data
@ApiModel
public class MachineInfoRequest {

	//页数
	@Range(min = 1,max = 1000,message = "无效的参数[page_num]")
	@ApiModelProperty(value = "用户名", required = true)
	private Integer page_num;

	//页数大小
	@Range(min = 1,max = 50,message = "无效的参数[page_size]")
	@ApiModelProperty(value = "密码", required = true)
	private Integer page_size;


}
