package com.hik.seckill.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wangJinChang on 2019/12/3 16:54
 * 商品视图对象(秒杀活动)
 */
@Data
public class ItemVO implements Serializable {

    private static final long serialVersionUID = -7153392388203721677L;

    private Integer id;         //商品id

    private String gName;       //商品名称

    private BigDecimal gPrice;  //商品价格

    private Integer gCount;     //商品库存

    private String gType;       //商品类型

    private Integer sales;      //商品销量

    private String gImageUrl;   //商品描述图片

    private String startTime;   //活动开始时间

    private String endTime;     //活动开始时间

    private PromoVO promoVO;    //使用聚合模型,如果promoVO不为空,则表示其拥有还未结束的秒杀聚合对象
}
