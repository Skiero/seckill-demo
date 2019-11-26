package com.hik.seckill.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * Created by wangJinChang on 2019/11/4 19:19
 * 用户实体类
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

    private Integer uid;//主键
    @NotBlank(message = "用户账号不能为空")
    private String account;//账号
    @NotBlank(message = "用户密码不能为空")
    private String pwd;//密码

    private String phone;//手机

    private Integer age;//年龄

    private Integer gender;//性别 1表示男 2表示女 3表示未选择
}
