package com.hik.seckill.model.vo;

import java.io.Serializable;

/**
 * Created by wangJinChang on 2019/11/4 19:18
 * 结果视图对象  0表示成功，1表示失败，2表示其他
 */
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = -5652500020193204790L;

    private String code;

    private String msg;

    private T data;

    private ResultVO(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResultVO success(String msg) {
        return new ResultVO<>("0", msg, null);
    }

    public static <T> ResultVO<T> success(String msg, T data) {
        return new ResultVO<>("0", msg, data);
    }

    public static <T> ResultVO<T> error(String msg) {
        return new ResultVO<>("1", msg, null);
    }

    public static <T> ResultVO<T> error(String code, String msg) {
        return new ResultVO<>(code, msg, null);
    }

    public static <T> ResultVO<T> error(String code, String msg, T data) {
        return new ResultVO<>(code, msg, data);
    }

    public static <T> ResultVO<T> other(String msg) {
        return new ResultVO<>("3", msg, null);
    }
}
