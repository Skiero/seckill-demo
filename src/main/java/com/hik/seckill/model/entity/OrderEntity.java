package com.hik.seckill.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangJinChang on 2019/11/25 18:58
 * 订单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    private Integer id;             //订单ID

    private Long orderId;           //订单ID

    private Integer userId;         //用户ID

    private Integer goodsId;        //商品ID

    private String goodsName;       //商品名称

    private String goodsType;       //商品类别

    private Integer shoppingCount;  //购买数量

    private String address;         //收货地址

    private BigDecimal orderPrice;  //购买的金额(商品下单时的单价 X 数量 => itemPrice X shoppingCount)

    private BigDecimal itemPrice;   //创建订单时商品的单价,如果promoId非空,则是秒杀单价

    private Integer promoId;        //若非空,则表示以秒杀方式下单

    private Integer status;         //状态码 1表示启用 2表示禁用

    private String remark;          //备注

    private Integer createId;       //创建人

    private Date createTime;        //创建时间

    private Integer updateId;       //更新人

    private Date updateTime;        //更新时间
}
