package com.hik.seckill.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wangJinChang on 2019/11/22 14:33
 * 商品信息模糊查询请求参数
 */
@Data
@ApiModel
public class GoodsFuzzyQueryParam {
    @ApiModelProperty(value = "商品自增主键", notes = "商品ID不能小于1", dataType = "Integer", position = 1)
    private Integer id;//商品自增主键

    @ApiModelProperty(value = "商品名称", notes = "商品名称不能有空串", dataType = "String", position = 2)
    private String gName;//商品名称

    @ApiModelProperty(value = "商品主键", notes = "商品主键不能有空串", dataType = "String", position = 3)
    private String gIndexCode;//商品主键

    @ApiModelProperty(value = "商品类别", notes = "商品类别不能有空串", dataType = "String", position = 4)
    private String gType;//商品类别

    @ApiModelProperty(value = "商品库存", notes = "商品库存不能小于1", dataType = "Integer", position = 5)
    private Integer gCount;//商品库存

    @ApiModelProperty(value = "商品状态码", notes = "1 上架  2 下架", dataType = "Integer", position = 5)
    private Integer status;//状态码
}
