package com.hik.seckill.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by wangJinChang on 2019/11/4 19:20
 * 用户视图对象
 */
@Data
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = -3779153660741880849L;

    private Integer uid;//主键

    private String account;//账号

    private String phone;//手机

    private Integer age;//年龄

    private Integer gender;//性别 1表示男 2表示女 3表示未选择性别

    private Integer status;//状态码 1表示启用 2表示禁用
}
