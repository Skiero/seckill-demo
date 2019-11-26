package com.hik.seckill.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by wangJinChang on 2019/11/25 19:56
 * 订单信息入参
 */
@Data
public class OrderInfoParam {

    private Integer id;//订单ID

    @NotNull(message = "商品ID(购物车ID)不能为空")
    private Integer goodsId;//商品ID

    @NotBlank(message = "商品名称不能为空")
    private String goodsName;//商品名称

    private String goodsType;//商品类别

    private Integer shoppingCount;//购买数量

    @NotBlank(message = "收获地址不能为空")
    private String address;//收货地址
}
