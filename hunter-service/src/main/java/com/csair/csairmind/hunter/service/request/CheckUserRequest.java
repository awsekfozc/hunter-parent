package com.csair.csairmind.hunter.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by zhangcheng
 * cookie插件用户信息请求
 */
@Data
@ApiModel
public class CheckUserRequest {

	//用户名
	@NotBlank(message = "参数[username]不能为空")
	@ApiModelProperty(value = "用户名", required = true)
	private String username;

	//密码
	@NotBlank(message = "参数[pwd]不能为空")
	@ApiModelProperty(value = "密码", required = true)
	private String pwd;


}
