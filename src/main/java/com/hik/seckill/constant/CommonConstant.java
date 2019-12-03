package com.hik.seckill.constant;

/**
 * Created by wangJinChang on 2019/11/23 15:07
 * 通用静态常量
 */
public interface CommonConstant {

    String COOKIE_OTP_TOKEN = "otp";                    //用户登录时cookie的key
    String COOKIE_USER_LOGIN_TOKEN = "user-login";      //用户登录时cookie的key

    Integer ADD_SHOPPING_CART = 1;                      //购物车中增加商品信息
    Integer MODIFY_SHOPPING_CART = 2;                   //修改购物车中的商品信息
    Integer REMOVE_SHOPPING_CART = 3;                   //删除购物车中的商品信息

    Integer ACTIVITY_NOT_STARTED = 1;                   //秒杀活动还未开始
    Integer ACTIVITY_IN_PROGRESS = 2;                   //秒杀活动进行中
    Integer ACTIVITY_IS_ENDED = 3;                      //秒杀活动状已经结束
}
