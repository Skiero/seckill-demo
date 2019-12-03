package com.hik.seckill.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wangJinChang on 2019/11/22 16:17
 * 商品信息展示对象
 */
@Data
public class GoodsInfoVO implements Serializable {

    private static final long serialVersionUID = -5911093906956730414L;

    private Integer id;         //商品自增主键

    private String gName;       //商品名称

    private String gIndexCode;  //商品主键

    private String gType;       //商品类别

    private Integer gCount;     //商品库存

    private Integer sales;      //商品售量

    private BigDecimal gPrice;  //商品售价

    private String gImageUrl;   //图片地址
}
