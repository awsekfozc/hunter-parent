package com.csair.csairmind.hunter.common.vo;

import lombok.Data;

/**
 * Created by fate
 */
@Data
public class CookiePlugVo {

    //账号
    private String username;

    //密码
    private String password;

    //登陆地址
    private String url;

    //账号框位置
    private String username_position;

    //密码框位置
    private String pwd_position;

    //登陆按钮位置
    private String sub_position;
}
