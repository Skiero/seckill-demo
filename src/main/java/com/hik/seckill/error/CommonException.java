package com.hik.seckill.error;

/**
 * Created by wangJinChang on 2019/11/7 10:04
 * 自定义异常类，用于业务异常处理
 */
public class CommonException extends Exception {

    private static final long serialVersionUID = -3635381845905285336L;

    private String errCode;

    private String errMsg;

    public CommonException(String errCode, String errMsg) {
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
