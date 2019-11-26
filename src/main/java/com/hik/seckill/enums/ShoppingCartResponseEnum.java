package com.hik.seckill.enums;

/**
 * Created by wangJinChang on 2019/11/26 11:24
 * 购物车相关操作的枚举返回
 */
public enum ShoppingCartResponseEnum {

    FAILURE(-1, "失败"),
    SUCCESS(1, "成功"),
    ERROR(2, "错误"),
    OTHER(1, ""),

    ;

    int code;
    String message;

    ShoppingCartResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
