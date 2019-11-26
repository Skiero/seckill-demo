package com.hik.seckill.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by wangJinChang on 2019/11/25 18:58
 * 订单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    private Integer id;//订单ID

    private Integer userId;//用户ID

    private Integer goodsId;//商品ID

    private String goodsName;//商品名称

    private String goodsType;//商品类别

    private Integer shoppingCount;//购买数量

    private String address;//收货地址

    private Integer status;//状态码 1表示启用 2表示禁用

    private String remark;//备注

    private Integer createId;//创建人

    private Date createTime;//创建时间

    private Integer updateId;//更新人

    private Date updateTime;//更新时间
}
