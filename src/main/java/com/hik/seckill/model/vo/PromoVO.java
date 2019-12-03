package com.hik.seckill.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangJinChang on 2019/12/3 16:55
 * 秒杀活动视图对象
 */
@Data
public class PromoVO implements Serializable {

    private static final long serialVersionUID = -3519228554698447538L;

    private Integer id;                 //id

    private Integer status;             //秒杀活动状态 1 还未开始 2 进行中 3 已经结束

    private String promoName;           //秒杀活动名称

    private Date startTime;             //秒杀活动开始时间

    private Date endTime;               //秒杀活动结束时间

    private Integer itemId;             //秒杀活动的适用商品

    private BigDecimal promoItemPrice;  //秒杀活动的商品价格
}
