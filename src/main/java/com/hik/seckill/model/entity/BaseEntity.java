package com.hik.seckill.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by wangJinChang on 2019/11/5 20:43
 * BaseEntity，定义了创建人、创建时间、修改人、修改时间、状态和备注信息
 */
@Data
@NoArgsConstructor
public class BaseEntity {

    private Integer status;//状态码 1表示启用 2表示禁用

    private String remark;//备注

    private Integer createId;//创建人

    private Date createTime;//创建时间

    private Integer updateId;//更新人

    private Date updateTime;//更新时间
}
