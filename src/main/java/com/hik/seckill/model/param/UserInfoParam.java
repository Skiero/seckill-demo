package com.hik.seckill.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by wangJinChang on 2019/11/7 14:35
 * 用户信息请求参数
 */
@Data
@ApiModel
public class UserInfoParam {

    @ApiModelProperty(value = "主键", notes = "主键", dataType = "Integer", position = 1)
    @NotNull(message = "用户ID不能为空")
    private Integer uid;//主键

    @ApiModelProperty(value = "账号", notes = "账号", dataType = "String", required = true, position = 2)
    @NotBlank(message = "用户名不能为空")
    private String account;//账号

    @ApiModelProperty(value = "密码", notes = "密码", dataType = "String", required = true, position = 3)
    private String pwd;//密码

    @ApiModelProperty(value = "手机", notes = "手机", dataType = "String", required = true, position = 4)
    @NotBlank(message = "用户手机不能为空")
    @Pattern(regexp = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$", message = "输入的手机格式不正确")
    private String phone;//手机

    @ApiModelProperty(value = "年龄", notes = "年龄", dataType = "Integer", position = 5)
    @Range(min = 0, max = 150, message = "输入的年龄不合法")
    private Integer age;//年龄

    @ApiModelProperty(value = "性别", notes = "1表示男 2表示女 3表示未选择", dataType = "Integer", position = 6)
    @Range(min = 1, max = 3, message = "输入的性别不合法")
    private Integer gender;//性别 1表示男 2表示女 3表示未选择性别

    @ApiModelProperty(value = "状态码", notes = "1表示启用 2表示禁用", dataType = "Integer", position = 7)
    @Range(min = 1, max = 2, message = "输入的状态不合法")
    private Integer status;//状态码 1表示启用 2表示禁用
}
