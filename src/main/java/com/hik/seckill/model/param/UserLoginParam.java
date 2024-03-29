package com.hik.seckill.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by wangJinChang on 2019/11/7 19:00
 * 用户登录请求参数
 */
@Data
public class UserLoginParam {

    @ApiModelProperty(value = "手机号码", notes = "手机号码", dataType = "String", required = true, position = 1)
    @NotBlank(message = "手机号码不能为空")
    private String phone;//账号

    @ApiModelProperty(value = "密码", notes = "用户密码", dataType = "String", required = true, position = 2)
    @NotBlank(message = "用户密码不能为空")
    private String pwd;//密码
}
