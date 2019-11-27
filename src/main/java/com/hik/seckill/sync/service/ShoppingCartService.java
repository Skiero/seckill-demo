package com.hik.seckill.sync.service;

import com.hik.seckill.enums.ShoppingCartResponseEnum;
import com.hik.seckill.mapper.CartEntityMapper;
import com.hik.seckill.model.entity.CartEntity;
import com.hik.seckill.model.vo.CartInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by wangJinChang on 2019/11/26 10:46
 * 多线程执行购物车业务
 */
@Component
@Slf4j
public class ShoppingCartService {
    @Autowired
    private CartEntityMapper cartEntityMapper;

    /**
     * 增加购物车信息到数据库
     *
     * @param cartInfoVO 购物车信息视图对象
     * @param userId     当前登录的用户ID
     * @return 添加到数据库时的执行结果枚举
     */
    public ShoppingCartResponseEnum addShoppingCartToDataBase(CartInfoVO cartInfoVO, Integer userId) {
        CartEntity cartEntity = new CartEntity();
        BeanUtils.copyProperties(cartInfoVO, cartEntity);
        cartEntity.setId(null);
        cartEntity.setUserId(userId);
        cartEntity.setCreateId(userId);
        cartEntity.setCreateTime(new Date());
        cartEntity.setStatus(1);
        int row;
        try {
            row = cartEntityMapper.insertSelective(cartEntity);
        } catch (Exception e) {
            log.error("SYNC ShoppingCartService addShoppingCartToDataBase has error , message is {} , caused by {} ", e.getMessage(), e.getCause());
            return ShoppingCartResponseEnum.ERROR;
        }
        if (row > 0) {
            ShoppingCartResponseEnum.OTHER.setMessage(String.valueOf(cartEntity.getId()));
            return ShoppingCartResponseEnum.OTHER;
        }
        return ShoppingCartResponseEnum.FAILURE;
    }

    /**
     * 修改数据库中的购物车信息
     *
     * @param cartInfoVO 购物车信息视图对象
     * @param userId     当前登录的用户ID
     * @return 修改数据库中的数据时的执行结果枚举
     */
    public ShoppingCartResponseEnum modifyShoppingCartFromDataBase(CartInfoVO cartInfoVO, Integer userId) {
        CartEntity cartEntity = new CartEntity();
        BeanUtils.copyProperties(cartInfoVO, cartEntity);
        cartEntity.setUpdateId(userId);
        cartEntity.setUpdateTime(new Date());
        int row;
        try {
            row = cartEntityMapper.updateByPrimaryKeySelective(cartEntity);
        } catch (Exception e) {
            log.error("SYNC ShoppingCartService modifyShoppingCartFromDataBase has error , message is {} , caused by {} ", e.getMessage(), e.getCause());
            return ShoppingCartResponseEnum.ERROR;
        }

        if (row > 0) {
            return ShoppingCartResponseEnum.SUCCESS;
        }
        return ShoppingCartResponseEnum.FAILURE;
    }

    /**
     * 删除数据库中的购物车信息(购物车的具体商品信息)
     *
     * @param cartInfoVO 购物车信息视图对象
     * @param userId     当前登录的用户ID
     * @return 删除数据中的数据时的执行结果枚举
     */
    public ShoppingCartResponseEnum removeShoppingCartFromDataBase(CartInfoVO cartInfoVO, Integer userId) {
        int row;
        try {
            row = cartEntityMapper.remove(cartInfoVO.getId(), userId);
        } catch (Exception e) {
            log.error("SYNC ShoppingCartService removeShoppingCartFromDataBase has error , message is {} , caused by {} ", e.getMessage(), e.getCause());
            return ShoppingCartResponseEnum.ERROR;
        }
        if (row > 0) {
            return ShoppingCartResponseEnum.SUCCESS;
        }
        return ShoppingCartResponseEnum.FAILURE;
    }
}
