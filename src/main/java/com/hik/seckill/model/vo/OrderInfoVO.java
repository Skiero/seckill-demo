package com.hik.seckill.model.vo;

import lombok.Data;

/**
 * Created by wangJinChang on 2019/11/25 19:57
 * 订单信息视图对象
 */
@Data
public class OrderInfoVO {

    private Integer id;//订单ID

    private Integer goodsId;//商品ID

    private String goodsName;//商品名称

    private String goodsType;//商品类别

    private Integer shoppingCount;//购买数量

    private String address;//收货地址
}
