package com.hik.seckill.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangJinChang on 2019/12/3 13:49
 * 秒杀活动实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoEntity {

    private Integer id;                 //id

    private Integer status;             //秒杀活动状态 1 还未开始 2 进行中 3 已经结束

    private String promoName;           //秒杀活动名称

    private Date startTime;             //秒杀活动开始时间  //使用joda-time针对时间日期处理的库

    private Date endTime;               //秒杀活动结束时间  //使用joda-time针对时间日期处理的库

    private Integer itemId;             //秒杀活动的适用商品

    private BigDecimal promoItemPrice;  //秒杀活动的商品价格
}
