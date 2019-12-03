package com.hik.seckill.service;

import com.hik.seckill.enums.OrderInfoResponseEnum;
import com.hik.seckill.error.CommonException;
import com.hik.seckill.model.dto.CartInfoDTO;
import com.hik.seckill.model.dto.OrderInfoDTO;
import com.hik.seckill.model.vo.CartInfoVO;
import com.hik.seckill.model.vo.OrderInfoVO;
import com.hik.seckill.model.vo.PageVO;

/**
 * Created by wangJinChang on 2019/11/23 17:04
 * 购买支付业务层接口
 */
public interface IShoppingService {
    /**
     * 添加购物车
     *
     * @param cartInfoDTO 购物车信息数据传输对象
     * @param userId      当前登录用户ID
     * @return 购物车信息的分页显示视图对象
     */
    PageVO<CartInfoVO> addShoppingCart(CartInfoDTO cartInfoDTO, Integer userId) throws CommonException;

    /**
     * 修改购物车
     *
     * @param cartInfoDTO 购物车信息数据传输对象
     * @param userId      当前登录用户ID
     * @return 购物车信息的分页显示视图对象
     */
    PageVO<CartInfoVO> modifyShoppingCart(CartInfoDTO cartInfoDTO, Integer userId) throws CommonException;

    /**
     * 移除购物车中的商品信息
     *
     * @param cartInfoDTO 购物车信息数据传输对象
     * @param userId      当前登录用户ID
     * @return 购物车信息的分页显示视图对象
     */
    PageVO<CartInfoVO> removeCart(CartInfoDTO cartInfoDTO, Integer userId) throws CommonException;

    /**
     * 获取购物车信息
     *
     * @param userId 当前登录的用户ID
     * @return 购物车信息的分页显示视图对象
     */
    PageVO<CartInfoVO> getShoppingCart(Integer userId);

    /**
     * 下订单
     *
     * @param orderInfoDTO 订单信息数据传输对象
     * @param userId       当前登录的用户ID
     * @return 订单信息的展示对象
     */
    OrderInfoVO addOrderInfo(OrderInfoDTO orderInfoDTO, Integer userId) throws CommonException;

    /**
     * 秒杀活动下订单
     *
     * @param orderInfoDTO 订单信息数据传输对象
     * @param userId       当前登录的用户ID
     * @return 订单信息的展示对象
     */
    OrderInfoVO createOrder(OrderInfoDTO orderInfoDTO, Integer userId) throws CommonException;

    /**
     * 取消订单
     *
     * @param id     订单ID
     * @param userID 当前登录的用户ID
     * @return 订单相关的枚举返回
     */
    OrderInfoResponseEnum removeOrderInfo(Integer id, Integer userID);

    /**
     * 改订单
     *
     * @param orderInfoDTO 订单信息数据传输对象
     * @param UserId       当前登录的用户ID
     * @return 订单信息的展示对象
     */
    OrderInfoVO modifyOrderInfo(OrderInfoDTO orderInfoDTO, Integer UserId);

    /**
     * 查订单
     *
     * @param ids    订单ID
     * @param userId 当前登录的用户ID
     * @return 订单信息的分页显示视图对象
     */
    PageVO<OrderInfoVO> getOrderInfo(String ids, Integer userId);
}
