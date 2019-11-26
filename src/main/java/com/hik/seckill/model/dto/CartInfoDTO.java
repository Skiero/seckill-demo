package com.hik.seckill.model.dto;

import lombok.Data;

/**
 * Created by wangJinChang on 2019/11/23 17:28
 * 购物车数据传输对象
 */
@Data
public class CartInfoDTO {

    private Integer id;//购物车ID

    private Integer goodsId;//商品ID

    private String goodsName;//商品名称

    private String goodsType;//商品类别

    private Integer shoppingCount;//购买数量
}
