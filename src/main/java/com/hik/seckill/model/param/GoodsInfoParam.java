package com.hik.seckill.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Created by wangJinChang on 2019/11/22 16:12
 * 商品信息入参
 */
@Data
@ApiModel
public class GoodsInfoParam {

    @ApiModelProperty(value = "商品ID", notes = "商品ID", example = "100", dataType = "String", position = 3)
    @Min(value = 1, message = "商品ID最小为1")
    private Integer id;//商品库存

    @ApiModelProperty(value = "商品名称", notes = "商品名称", example = "goodsName", dataType = "String", position = 1)
    @NotEmpty(message = "输入的商品名称不能为空")
    private String gName;//商品名称

    @ApiModelProperty(value = "商品类别", notes = "商品类别", example = "日用百货", dataType = "String", position = 2)
    @Pattern(regexp = "^((?! ).)*$", message = "输入的商品类别不能包含空串")
    private String gType;//商品类别

    @ApiModelProperty(value = "商品库存", notes = "商品库存", example = "100", dataType = "String", position = 3)
    @Min(value = 1, message = "商品库存最小为1")
    private Integer gCount;//商品库存
}
