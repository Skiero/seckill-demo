package com.hik.seckill.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wangJinChang on 2019/11/4 19:19
 * 用户查询请求参数
 */
@Data
@ApiModel
public class UserQueryParam {

    @ApiModelProperty(value = "用户ID", dataType = "Integer", position = 1)
    private Integer uid;

    @ApiModelProperty(value = "用户账号", dataType = "String", position = 2)
    private String account;

    @ApiModelProperty(value = "用户密码", dataType = "String", position = 2)
    private String pwd;

    @ApiModelProperty(value = "手机", dataType = "String", position = 3)
    private String phone;

    @ApiModelProperty(value = "年龄", dataType = "Integer", position = 4)
    private Integer age;

    @ApiModelProperty(value = "性别", dataType = "Integer", position = 5)
    private Integer gender;

    @ApiModelProperty(value = "状态码", dataType = "Integer", position = 6)
    private Integer status;
}
