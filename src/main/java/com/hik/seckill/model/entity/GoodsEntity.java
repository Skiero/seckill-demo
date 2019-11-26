package com.hik.seckill.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by wangJinChang on 2019/11/21 19:03
 * 商品实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsEntity extends BaseEntity {

    private Integer id;//商品自增主键

    private String gName;//商品名称

    private String gIndexCode;//商品主键

    private String gType;//商品类别

    private Integer gCount;//商品库存
}
