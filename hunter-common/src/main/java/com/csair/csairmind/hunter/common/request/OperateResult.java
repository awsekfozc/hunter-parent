package com.csair.csairmind.hunter.common.request;

import com.csair.csairmind.hunter.common.enums.OperateCodeHolder;
import com.csair.csairmind.hunter.common.response.ApiResponse;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhengcheng
 * 响应实体
 */
@Data
public class OperateResult implements Serializable {


    private ApiResponse response;

    private OperateCodeHolder operateCodeHolder;


    public boolean isSuccess() {
        return "1".equals(operateCodeHolder.getCode());
    }

}
