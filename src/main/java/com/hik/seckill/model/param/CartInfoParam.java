package com.hik.seckill.model.param;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by wangJinChang on 2019/11/25 19:56
 * 购物车信息入参
 */
@Data
@Api
public class CartInfoParam {

    @ApiModelProperty(value = "购物车ID", dataType = "Integer", position = 1)
    private Integer id;//购物车ID

    @ApiModelProperty(value = "商品ID", dataType = "Integer", position = 2)
    @NotNull(message = "商品ID不能为空")
    private Integer goodsId;//商品ID

    @ApiModelProperty(value = "商品名称", dataType = "String", position = 3)
    @NotBlank(message = "商品名称不能为空")
    private String goodsName;//商品名称

    @ApiModelProperty(value = "商品类别", dataType = "String", position = 4)
    private String goodsType;//商品类别

    @ApiModelProperty(value = "购买数量", dataType = "Integer", position = 5)
    @NotNull(message = "购买数量不能为空")
    @Range(min = 1, max = 100, message = "商品购买数量在1~100之间")
    private Integer shoppingCount;//购买数量
}
