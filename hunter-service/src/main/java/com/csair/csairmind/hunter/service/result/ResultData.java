package com.csair.csairmind.hunter.service.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangcheng
 * 返回结果
 */
@Data
@ApiModel(value = "返回结果")
public class ResultData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //处理成功
    public static final String OK = "0000";
    public static final String OK_MSG = "操作成功";
    //其他错误
    public static final String ERR = "9999";
    //其他错误
    public static final String ERR_MSG = "操作失败";

    public static final ResultData SUCESS = new ResultData(OK, OK_MSG);


    @ApiModelProperty(value = "状态码")
    private String code = ERR;
    @ApiModelProperty(value = "状态描述")
    private String msg = "";
    @ApiModelProperty(value = "数据")
    private T data;


    /**
     * 失败
     *
     * @return
     */
    public static ResultData getFailResult() {
        return new ResultData(ERR, ERR_MSG);
    }

    /**
     * 失败
     *
     * @param message
     * @return
     */
    public static ResultData getFailResult(String message) {
        return new ResultData(ERR, message);
    }

    /**
     * 成功
     *
     * @param message
     * @return
     */
    public static ResultData getSuccessResult(String message) {
        return new ResultData(OK, message);
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultData getSuccessData(T data) {
        return new ResultData(OK, OK_MSG, data);
    }

    /**
     * 成功
     *
     * @param data
     * @param message
     * @return
     */
    public static <T> ResultData getSuccessResult(T data, String message) {
        return new ResultData(OK, message, data);
    }

    public ResultData() {
    }

    public ResultData(String code, String message) {
        this.code = code;
        this.msg = message;
    }

    public ResultData(String code, String message, T result) {
        this.code = code;
        this.msg = message;
        this.data = result;
    }

    public ResultData(T result) {
        this(OK, "操作成功！", result);
    }

    public Object getData() {
        return data;
    }

    public ResultData setData(T data) {
        this.data = data;
        return this;
    }
}
