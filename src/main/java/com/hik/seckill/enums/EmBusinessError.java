package com.hik.seckill.enums;

/**
 * Created by wangJinChang on 2019/11/7 9:25
 * 业务异常枚举类
 */

public enum EmBusinessError {

    //10000开头    通用错误类型
    PARAMETER_VALIDATION_ERROR("10001", "参数不合法"),

    UNKNOWN_ERROR("10002", "未知错误"),

    USER_SERVICE_ERROR("10003", "系统错误"),

    GOODS_SERVICE_ERROR("10004", "系统错误"),

    SHOPPING_CART_SERVICE_ERROR("10006", "系统错误"),

    VALIDATION_ERROR("10005", "参数校验异常"),

    //20000开头    用户信息相关错误
    USER_NOT_EXIST("20001", "用户不存在"),

    USER_NOT_LOGIN("20002", "用户未登陆"),

    USER_LOGIN_FAIL("20003", "账号或密码错误"),

    USER_INFO_ERROR("20004", "用户信息不合法"),

    //30000开头    商品信息相关错误
    GOODS_NOT_EXIST("30001", "商品信息不存在"),

    GOODS_ID_NOT_NULL("30002", "商品ID不能为空"),

    //40000开头    购物车信息相关错误
    SHOPPING_CART_NOT_EXIST("40001", "购物车不存在"),

    CART_ITEM_NOT_EXIST("40002", "购物车中的具体商品信息不存在"),

    INSUFFICIENT_INVENTORY("40003", "库存不足"),

    GENERATE_ORDER_FAILED("40004", "生成订单失败"),

    REMOVE_SHOPPING_CART_FAILED("40005", "删除购物车信息失败"),

    ;

    private String errCode;
    private String errMsg;

    EmBusinessError(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

