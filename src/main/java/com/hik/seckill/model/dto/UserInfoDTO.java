package com.hik.seckill.model.dto;

import lombok.Data;

/**
 * Created by wangJinChang on 2019/11/7 14:35
 * 用户信息传输对象
 */
@Data
public class UserInfoDTO {

    private Integer uid;//主键

    private String account;//账号

    private String pwd;//密码

    private String phone;//手机

    private Integer age;//年龄

    private Integer gender;//性别 1表示男 2表示女 3表示未选择性别

    private Integer status;//状态码 1表示启用 2表示禁用
}
