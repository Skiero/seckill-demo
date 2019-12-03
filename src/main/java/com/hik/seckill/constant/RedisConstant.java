package com.hik.seckill.constant;

/**
 * Created by wangJinChang on 2019/11/23 15:06
 * Redis缓存相关的静态常量
 */
public interface RedisConstant {

    String OTP_TOKEN = "otp-token::";               //用户注册时获取验证码redis的key(和uuid拼接)

    String USER_LOGIN_TOKEN = "user-token::";       //用户登录时redis的key(和uuid拼接)

    String SHOPPING_CART_TOKEN = "shopping-cart::"; //购物车redis的key(和用户ID拼接)
}
