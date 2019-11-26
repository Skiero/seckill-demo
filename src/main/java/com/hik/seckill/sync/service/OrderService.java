package com.hik.seckill.sync.service;

import com.alibaba.fastjson.JSON;
import com.hik.seckill.constant.RedisConstant;
import com.hik.seckill.enums.EmBusinessError;
import com.hik.seckill.enums.OrderInfoResponseEnum;
import com.hik.seckill.error.CommonException;
import com.hik.seckill.mapper.CartEntityMapper;
import com.hik.seckill.model.vo.CartInfoVO;
import com.hik.seckill.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by wangJinChang on 2019/11/26 20:14
 * 多线程执行订单业务
 */
@Component
@Slf4j
public class OrderService {
    @Autowired
    private CartEntityMapper cartEntityMapper;

    /**
     * 订单生成后删除购物车相关信息
     *
     * @param goodId 购物车的信息ID
     * @param userId 当前登录的用户ID
     * @return 订单信息的枚举返回
     */
    public OrderInfoResponseEnum removeShoppingCart(Integer goodId, Integer userId) throws CommonException {
        //删除数据中的购物车信息
        int row = cartEntityMapper.remove(goodId, userId);
        if (row < 1) {
            log.error("OrderService removeShoppingCart from database has exception , code is {} , message is {} ", EmBusinessError.REMOVE_SHOPPING_CART_FAILED.getErrCode(), EmBusinessError.REMOVE_SHOPPING_CART_FAILED.getErrMsg());
            throw new CommonException(EmBusinessError.REMOVE_SHOPPING_CART_FAILED.getErrCode(), EmBusinessError.REMOVE_SHOPPING_CART_FAILED.getErrMsg());
        }
        //删除redis缓存
        String redisKey = String.format(RedisConstant.SHOPPING_CART_TOKEN + "%S", userId);
        String redisValue = RedisUtil.get(redisKey);
        //redis过期时,不进行处理;未过期时将删除redis缓存
        if (StringUtils.isBlank(redisValue)) {
            log.debug("OrderService get shopping cart information from redis is empty");
            return OrderInfoResponseEnum.SUCCESS;
        }
        List<CartInfoVO> cartInfoVOList = JSON.parseArray(redisValue, CartInfoVO.class);
        List<CartInfoVO> collect = cartInfoVOList.stream().filter(cartInfoVO -> Objects.equals(goodId, cartInfoVO.getGoodsId())).distinct().collect(Collectors.toList());
        cartInfoVOList.remove(collect.get(0));
        RedisUtil.set(redisKey, JSON.toJSONString(cartInfoVOList), 60 * 30);
        log.debug("OrderService get shopping cart information from redis is {} , size is {} , remove CartInfoVO success! ", JSON.toJSONString(cartInfoVOList), cartInfoVOList.size());
        return OrderInfoResponseEnum.SUCCESS;
    }
}
