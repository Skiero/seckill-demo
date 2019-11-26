package com.hik.seckill.model.vo;

import lombok.Data;

/**
 * Created by wangJinChang on 2019/11/23 17:12
 * 购物车数据视图对象
 */
@Data
public class CartInfoVO {

    private Integer id;//购物车ID

    private Integer goodsId;//商品ID

    private String goodsName;//商品名称

    private String goodsType;//商品类别

    private Integer shoppingCount;//购买数量
}
