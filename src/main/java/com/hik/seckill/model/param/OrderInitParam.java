package com.hik.seckill.model.param;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by wangJinChang on 2019/12/3 12:04
 * 秒杀下订单时入参
 */
@Data
public class OrderInitParam {

    @NotNull(message = "商品ID不能为空")
    @Min(value = 1, message = "商品ID最小为1")
    private Integer itemId;     //商品ID

    @NotNull(message = "购买数量不能为空")
    @Min(value = 1, message = "购买数量最小为1")
    private Integer amount;     //购买数量

    @NotEmpty(message = "收货地址不能为空")
    private String address;     //收货地址

    private Integer promoId;    //秒杀商品标识
}
