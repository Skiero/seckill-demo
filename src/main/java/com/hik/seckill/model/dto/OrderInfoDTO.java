package com.hik.seckill.model.dto;

import lombok.Data;

/**
 * Created by wangJinChang on 2019/11/25 19:22
 * 订单信息数据传输对象
 */
@Data
public class OrderInfoDTO {

    private Integer id;//订单ID

    private Integer userId;//用户ID

    private Integer goodsId;//商品ID

    private String goodsName;//商品名称

    private String goodsType;//商品类别

    private Integer shoppingCount;//购买数量

    private String address;//收货地址
}
