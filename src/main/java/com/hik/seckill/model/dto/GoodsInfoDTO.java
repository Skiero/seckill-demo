package com.hik.seckill.model.dto;

import lombok.Data;

/**
 * Created by wangJinChang on 2019/11/22 16:12
 * 商品信息数据传递对象
 */
@Data
public class GoodsInfoDTO {

    private Integer id;//商品自增主键

    private String gName;//商品名称

    private String gIndexCode;//商品主键

    private String gType;//商品类别

    private Integer gCount;//商品库存
}
