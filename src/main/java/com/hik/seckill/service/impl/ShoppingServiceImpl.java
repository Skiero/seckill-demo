package com.hik.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hik.seckill.constant.CommonConstant;
import com.hik.seckill.constant.RedisConstant;
import com.hik.seckill.enums.EmBusinessError;
import com.hik.seckill.enums.OrderInfoResponseEnum;
import com.hik.seckill.enums.ShoppingCartResponseEnum;
import com.hik.seckill.error.CommonException;
import com.hik.seckill.mapper.*;
import com.hik.seckill.model.dto.CartInfoDTO;
import com.hik.seckill.model.dto.OrderInfoDTO;
import com.hik.seckill.model.entity.*;
import com.hik.seckill.model.vo.CartInfoVO;
import com.hik.seckill.model.vo.OrderInfoVO;
import com.hik.seckill.model.vo.PageVO;
import com.hik.seckill.service.IShoppingService;
import com.hik.seckill.sync.CallableJob;
import com.hik.seckill.sync.Executor;
import com.hik.seckill.sync.RunnableJob;
import com.hik.seckill.utils.RandomUtil;
import com.hik.seckill.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by wangJinChang on 2019/11/23 17:04
 * 购买支付业务层实现类
 */
@Service
@Slf4j
public class ShoppingServiceImpl implements IShoppingService {

    @Autowired
    private CartEntityMapper cartEntityMapper;

    @Autowired
    private OrderEntityMapper orderEntityMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PromoEntityMapper promoEntityMapper;

    @Override
    public PageVO<CartInfoVO> addShoppingCart(CartInfoDTO cartInfoDTO, Integer userId) throws CommonException {
        /*
          判断购物车是否存在:
          A.不存在==>创建购物车;
          B.存在==>判断购物车中是否存在当前商品:
               a.不存在 ==>添加商品信息:
               b.存在   ==>修改商品数量。
         */
        String redisKey = String.format(RedisConstant.SHOPPING_CART_TOKEN + "%S", userId);
        String redisValue = RedisUtil.get(redisKey);
        List<CartInfoVO> shoppingCart;

        if (StringUtils.isBlank(redisValue)) {
            //购物车不存在,创建购物车,添加商品信息
            shoppingCart = Lists.newArrayList();
            CartInfoVO cartInfoVO = new CartInfoVO();
            BeanUtils.copyProperties(cartInfoDTO, cartInfoVO);

            //异步将购物车的信息保存至数据库
            CallableJob callableJob = new CallableJob(CommonConstant.ADD_SHOPPING_CART, cartInfoVO, userId);
            Future<ShoppingCartResponseEnum> future = Executor.submit(callableJob);
            try {
                ShoppingCartResponseEnum responseEnum = future.get();
                if (responseEnum.getCode() != ShoppingCartResponseEnum.SUCCESS.getCode()) {
                    log.error("购物车添加商品时,往数据库保存数据时失败,请重试!");
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("购物车添加商品时发生错误,错误信息是 {} , 原因是 {} ", e.getMessage(), e.getCause());
            }
        } else {
            //购物车存在
            shoppingCart = JSON.parseArray(redisValue, CartInfoVO.class);
            //判断购物车中是否有当前商品
            List<CartInfoVO> cartInfoVOList = shoppingCart.stream().filter(cartInfoVO -> Objects.equals(cartInfoVO.getGoodsId(), cartInfoDTO.getGoodsId())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(cartInfoVOList)) {
                //当前商品不存在,添加商品信息
                CartInfoVO cartInfoVO = new CartInfoVO();
                BeanUtils.copyProperties(cartInfoDTO, cartInfoVO);

                //异步将购物车的信息保存至数据库
                CallableJob callableJob = new CallableJob(CommonConstant.ADD_SHOPPING_CART, cartInfoVO, userId);
                Future<ShoppingCartResponseEnum> future = Executor.submit(callableJob);
                try {
                    ShoppingCartResponseEnum responseEnum = future.get();
                    if (responseEnum.getCode() != ShoppingCartResponseEnum.SUCCESS.getCode()) {
                        log.error("购物车添加商品时,往数据库保存数据时失败,请重试!");
                    }
                } catch (InterruptedException | ExecutionException e) {
                    log.error("购物车添加商品时发生错误,错误信息是 {} , 原因是 {} ", e.getMessage(), e.getCause());
                }
                //返回的购物车自增主键进行赋值
                cartInfoVO.setId(Integer.parseInt(ShoppingCartResponseEnum.OTHER.getMessage()));
                shoppingCart.add(cartInfoVO);
            } else {
                //商品信息存在,修改商品数量
                CartInfoVO cartInfoVO = cartInfoVOList.get(0);
                cartInfoVO.setShoppingCount(cartInfoDTO.getShoppingCount() + cartInfoVO.getShoppingCount());
                //异步将购物车的信息保存至数据库
                CallableJob callableJob = new CallableJob(CommonConstant.MODIFY_SHOPPING_CART, cartInfoVO, userId);
                Future<ShoppingCartResponseEnum> future = Executor.submit(callableJob);
                try {
                    ShoppingCartResponseEnum responseEnum = future.get();
                    if (responseEnum.getCode() != ShoppingCartResponseEnum.SUCCESS.getCode()) {
                        log.error("购物车添加商品时,往数据库保存数据时失败,请重试!");
                    }
                } catch (InterruptedException | ExecutionException e) {
                    log.error("购物车添加商品时发生错误,错误信息是 {} , 原因是 {} ", e.getMessage(), e.getCause());
                }
            }
        }
        //将购物车的信息保存至redis缓存
        RedisUtil.set(redisKey, JSON.toJSONString(shoppingCart), 60 * 30);
        return this.getCartInfoVOPageVO(shoppingCart);
    }

    @Override
    public PageVO<CartInfoVO> modifyShoppingCart(CartInfoDTO cartInfoDTO, Integer userId) throws CommonException {
        PageVO<CartInfoVO> shoppingCart = this.getShoppingCart(userId);
        List<CartInfoVO> cartInfoVOList = shoppingCart.getList();
        if (CollectionUtils.isEmpty(cartInfoVOList)) {
            log.error("ShoppingServiceImpl removeCart has exception , cartInfoVOList is empty!");
            throw new CommonException(EmBusinessError.SHOPPING_CART_NOT_EXIST.getErrCode(), EmBusinessError.SHOPPING_CART_NOT_EXIST.getErrMsg());
        }
        List<CartInfoVO> collect = cartInfoVOList.stream().filter(cartInfoVO -> Objects.equals(cartInfoVO.getId(), cartInfoDTO.getId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            log.error("ShoppingServiceImpl removeCart has exception , shopping cart item is not exist!");
            throw new CommonException(EmBusinessError.CART_ITEM_NOT_EXIST.getErrCode(), EmBusinessError.CART_ITEM_NOT_EXIST.getErrMsg());
        }
        collect.get(0).setShoppingCount(cartInfoDTO.getShoppingCount());
        shoppingCart.setList(cartInfoVOList);
        //更新redis缓存
        RedisUtil.set(String.format(RedisConstant.SHOPPING_CART_TOKEN + "%S", userId), JSON.toJSONString(cartInfoVOList), 60 * 30);
        //异步更新数据库中的购物车数据
        CallableJob callableJob = new CallableJob(CommonConstant.MODIFY_SHOPPING_CART, collect.get(0), userId);
        Executor.submit(callableJob);
        return shoppingCart;
    }

    @Override
    public PageVO<CartInfoVO> removeCart(CartInfoDTO cartInfoDTO, Integer userId) throws CommonException {
        PageVO<CartInfoVO> shoppingCart = this.getShoppingCart(userId);
        List<CartInfoVO> cartInfoVOList = shoppingCart.getList();
        if (CollectionUtils.isEmpty(cartInfoVOList)) {
            log.error("ShoppingServiceImpl removeCart has exception , cartInfoVOList is empty!");
            throw new CommonException(EmBusinessError.SHOPPING_CART_NOT_EXIST.getErrCode(), EmBusinessError.SHOPPING_CART_NOT_EXIST.getErrMsg());
        }
        List<CartInfoVO> collect = cartInfoVOList.stream().filter(cartInfoVO -> Objects.equals(cartInfoVO.getId(), cartInfoDTO.getId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            log.error("ShoppingServiceImpl removeCart has exception , shopping cart item is not exist!");
            throw new CommonException(EmBusinessError.CART_ITEM_NOT_EXIST.getErrCode(), EmBusinessError.CART_ITEM_NOT_EXIST.getErrMsg());
        }
        CartInfoVO cartInfoVO = collect.get(0);
        cartInfoVOList.remove(cartInfoVO);
        shoppingCart.setList(cartInfoVOList);
        //更新redis缓存
        RedisUtil.set(String.format(RedisConstant.SHOPPING_CART_TOKEN + "%S", userId), JSON.toJSONString(cartInfoVOList), 60 * 30);
        //异步更新数据库中的购物车数据
        CallableJob callableJob = new CallableJob(CommonConstant.REMOVE_SHOPPING_CART, collect.get(0), userId);
        Executor.submit(callableJob);
        return shoppingCart;
    }

    @Override
    public PageVO<CartInfoVO> getShoppingCart(Integer userId) {
        //首先从redis中获取购物车信息,若redis中没有购物车信息(redis过期),则从数据库中获取
        String redisKey = String.format(RedisConstant.SHOPPING_CART_TOKEN + "%S", userId);
        String redisValue = RedisUtil.get(redisKey);
        List<CartInfoVO> cartInfoVOList = Lists.newArrayList();
        //redis中有购物车信息
        if (StringUtils.isNotBlank(redisValue)) {
            cartInfoVOList = JSON.parseArray(redisValue, CartInfoVO.class);
            return this.getCartInfoVOPageVO(cartInfoVOList);
        }
        //redis中没有购物车信息
        List<CartEntity> cartEntityList = cartEntityMapper.selectByUserId(userId);
        cartInfoVOList = this.getCartInfoVOList(cartInfoVOList, cartEntityList);
        //更新redis缓存
        RedisUtil.set(redisKey, JSON.toJSONString(cartInfoVOList), 60 * 30);
        return this.getCartInfoVOPageVO(cartInfoVOList);
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public OrderInfoVO addOrderInfo(OrderInfoDTO orderInfoDTO, Integer userId) throws CommonException {
        //采用下单减库存策略,若减库存失败,则告知下单失败;若减库存成功,则进行生成订单操作

        //首先判断购物车中是否有要购买商品的信息,若存在则购买,若不存在则返回购物车中无该商品
        PageVO<CartInfoVO> shoppingCart = this.getShoppingCart(userId);
        List<CartInfoVO> collect = shoppingCart.getList().stream().filter(cartInfoVO -> Objects.equals(orderInfoDTO.getGoodsId(), cartInfoVO.getGoodsId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            throw new CommonException(EmBusinessError.GOODS_NOT_EXIST.getErrCode(), EmBusinessError.GOODS_NOT_EXIST.getErrMsg());
        }

        //减订单,防止超卖
        int row = goodsMapper.reduce(orderInfoDTO.getGoodsId(), orderInfoDTO.getShoppingCount(), userId);
        if (row < 1) {
            throw new CommonException(EmBusinessError.INSUFFICIENT_INVENTORY.getErrCode(), EmBusinessError.INSUFFICIENT_INVENTORY.getErrMsg());
        }
        //减库存成功,生成订单
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderInfoDTO, orderEntity);
        orderEntity.setId(null);
        orderEntity.setUserId(userId);
        orderEntity.setCreateId(userId);
        orderEntity.setCreateTime(new Date());
        orderEntity.setStatus(1);
        int i = orderEntityMapper.insertSelective(orderEntity);
        if (i < 1) {
            throw new CommonException(EmBusinessError.GENERATE_ORDER_FAILED.getErrCode(), EmBusinessError.GENERATE_ORDER_FAILED.getErrMsg());
        }
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        BeanUtils.copyProperties(orderEntity, orderInfoVO);
        //赋值自增主键
        orderInfoVO.setId(orderEntity.getId());
        //删除购物车信息
        RunnableJob runnableJob = new RunnableJob(orderInfoDTO.getGoodsId(), userId);
        Executor.submit(runnableJob);
        return orderInfoVO;
    }

    @Transactional
    @Override
    public OrderInfoVO createOrder(OrderInfoDTO orderInfoDTO, Integer userId) throws CommonException {
        //下订单前进行合法性校验  商品是否存在 用户是否合法 活动信息是否合法
        GoodsEntity goodsEntity = goodsMapper.queryById(orderInfoDTO.getGoodsId());
        if (Objects.isNull(goodsEntity)) {
            throw new CommonException(EmBusinessError.GOODS_NOT_EXIST.getErrCode(), EmBusinessError.GOODS_NOT_EXIST.getErrMsg());
        }
        UserEntity userEntity = userMapper.queryByPrimaryKey(userId);
        if (Objects.isNull(userEntity)) {
            throw new CommonException(EmBusinessError.USER_NOT_EXIST.getErrCode(), EmBusinessError.USER_NOT_EXIST.getErrMsg());
        }
        PromoEntity promoEntity = null;
        Integer promoId = orderInfoDTO.getPromoId();
        if (!Objects.isNull(promoId)) {
            promoEntity = promoEntityMapper.selectByPrimaryKey(promoId);
            if (Objects.isNull(promoEntity) || !Objects.equals(promoId, promoEntity.getId())) {
                throw new CommonException(EmBusinessError.NOT_SECOND_KILL.getErrCode(), EmBusinessError.NOT_SECOND_KILL.getErrMsg());
            }
            if (Objects.equals(CommonConstant.ACTIVITY_NOT_STARTED, promoEntity.getStatus())) {
                throw new CommonException(EmBusinessError.ACTIVITY_NOT_STARTED.getErrCode(), EmBusinessError.ACTIVITY_NOT_STARTED.getErrMsg());
            }
            if (Objects.equals(CommonConstant.ACTIVITY_IS_ENDED, promoEntity.getStatus())) {
                throw new CommonException(EmBusinessError.ACTIVITY_IS_ENDED.getErrCode(), EmBusinessError.ACTIVITY_IS_ENDED.getErrMsg());
            }
        }
        //采用下单减库存策略
        int reduce = goodsMapper.reduce(orderInfoDTO.getGoodsId(), orderInfoDTO.getShoppingCount(), userId);
        if (reduce < 1) {
            throw new CommonException(EmBusinessError.INSUFFICIENT_INVENTORY.getErrCode(), EmBusinessError.INSUFFICIENT_INVENTORY.getErrMsg());
        }
        OrderEntity orderEntity = getOrderEntity(orderInfoDTO, userId, goodsEntity, promoEntity);
        if (Objects.isNull(orderEntity)) {
            throw new CommonException(EmBusinessError.SHOPPING_CART_SERVICE_ERROR.getErrCode(), EmBusinessError.SHOPPING_CART_SERVICE_ERROR.getErrMsg());
        }
        int insert = orderEntityMapper.insertSelective(orderEntity);
        if (insert < 1) {
            throw new CommonException(EmBusinessError.GENERATE_ORDER_FAILED.getErrCode(), EmBusinessError.GENERATE_ORDER_FAILED.getErrMsg());
        }
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        BeanUtils.copyProperties(orderEntity, orderInfoVO);
        //赋值自增主键
        orderInfoVO.setId(orderEntity.getId());
        return orderInfoVO;
    }

    @Override
    public OrderInfoResponseEnum removeOrderInfo(Integer id, Integer userID) {
        return null;
    }

    @Override
    public OrderInfoVO modifyOrderInfo(OrderInfoDTO orderInfoDTO, Integer UserId) {
        return null;
    }

    @Override
    public PageVO<OrderInfoVO> getOrderInfo(String ids, Integer userId) {
        List<OrderInfoVO> orderInfoVOList = Lists.newArrayList();
        //订单ID不存在,查询当前登录用户的所有订单信息
        if (StringUtils.isBlank(ids)) {
            List<OrderEntity> entityList = orderEntityMapper.selectAllOrder(userId);
            orderInfoVOList = getOrderInfoVOList(orderInfoVOList, entityList);
            return getOrderInfoVOPageVO(orderInfoVOList);
        }
        //订单ID存在,则按照订单ID查询
        String[] list = ids.split(",");
        List<Integer> idList = Lists.newArrayList();
        for (String s : list) {
            idList.add(Integer.valueOf(s));
        }
        List<OrderEntity> entityList = orderEntityMapper.selectByOrderId(idList, userId);
        orderInfoVOList = getOrderInfoVOList(orderInfoVOList, entityList);
        return getOrderInfoVOPageVO(orderInfoVOList);
    }

    /**
     * 获取CartInfoVOList
     *
     * @param cartInfoVOList 初始化的CartInfoVOList集合
     * @param cartEntityList CartEntity购物车实体类集合
     * @return CartInfoVOList集合
     */
    private List<CartInfoVO> getCartInfoVOList(List<CartInfoVO> cartInfoVOList, List<CartEntity> cartEntityList) {
        for (CartEntity cartEntity : cartEntityList) {
            cartInfoVOList = Lists.newArrayList();
            CartInfoVO cartInfoVO = new CartInfoVO();
            BeanUtils.copyProperties(cartEntity, cartInfoVO);
            cartInfoVOList.add(cartInfoVO);
        }
        return cartInfoVOList;
    }

    /**
     * 获取CartInfoVOPageVO
     *
     * @param shoppingCart shoppingCart购物车信息视图集合
     * @return CartInfoVOPageVO分页视图对象
     */
    private PageVO<CartInfoVO> getCartInfoVOPageVO(List<CartInfoVO> shoppingCart) {
        PageVO<CartInfoVO> pageVO = new PageVO<>();
        pageVO.setList(shoppingCart);
        pageVO.setPageNo(1);
        pageVO.setPageSize(20);
        pageVO.setTotal(shoppingCart.size());
        pageVO.setTotalPage(shoppingCart.size() / 20 + 1);
        return pageVO;
    }

    /**
     * 获取OrderInfoVOList
     *
     * @param orderInfoVOList 初始化的OrderInfoVOList集合
     * @param orderEntityList OrderEntity实体类集合
     * @return OrderInfoVOList集合
     */
    private List<OrderInfoVO> getOrderInfoVOList(List<OrderInfoVO> orderInfoVOList, List<OrderEntity> orderEntityList) {
        for (OrderEntity orderEntity : orderEntityList) {
            orderInfoVOList = Lists.newArrayList();
            OrderInfoVO orderInfoVO = new OrderInfoVO();
            BeanUtils.copyProperties(orderEntity, orderInfoVO);
            orderInfoVOList.add(orderInfoVO);
        }
        return orderInfoVOList;
    }

    /**
     * 获取OrderInfoVOPageVO
     *
     * @param orderInfoVOList OrderInfoVO集合
     * @return OrderInfoVOPageVO分页视图对象
     */
    private PageVO<OrderInfoVO> getOrderInfoVOPageVO(List<OrderInfoVO> orderInfoVOList) {
        PageVO<OrderInfoVO> pageVO = new PageVO<>();
        pageVO.setList(orderInfoVOList);
        pageVO.setPageNo(1);
        pageVO.setPageSize(20);
        pageVO.setTotal(orderInfoVOList.size());
        pageVO.setTotalPage(orderInfoVOList.size() / 20 + 1);
        return pageVO;
    }

    /**
     * 创建订单编号
     * 订单号16位,前8位为时间信息,中间6位为随机数,后2为为分库分表标记
     *
     * @return 订单编号
     */
    private long generateOrderNo() {
        StringBuilder sb = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        sb.append(nowDate);
        //凑足6位拼接序列
        String sequenceStr = String.valueOf(RandomUtil.randomLong(1, 9999));
        //序列值前面的几位补0
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            sb.append(0);
        }
        //将序列拼接上去,例如000001
        sb.append(sequenceStr);
        //最后2位为分库分表位
        sb.append(RandomUtil.randomLong(2));
        return Long.parseLong(sb.toString());
    }

    /**
     * 获取订单实体类
     *
     * @param orderInfoDTO 订单信息数据传输对象
     * @param userId       当前登录的用户ID
     * @param goodsEntity  商品信息实体类
     * @param promoEntity  秒杀活动实体类
     * @return 订单信息实体类
     */
    private OrderEntity getOrderEntity(OrderInfoDTO orderInfoDTO, Integer userId, GoodsEntity goodsEntity, PromoEntity promoEntity) {
        if (orderInfoDTO == null || goodsEntity == null) {
            return null;
        }
        //减库存成功,生成订单
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderInfoDTO, orderEntity);
        orderEntity.setOrderId(generateOrderNo());
        orderEntity.setUserId(userId);
        orderEntity.setCreateId(userId);
        orderEntity.setCreateTime(new Date());
        orderEntity.setStatus(1);
        orderEntity.setGoodsName(goodsEntity.getGName());
        orderEntity.setGoodsType(goodsEntity.getGType());
        orderEntity.setItemPrice(promoEntity == null ? goodsEntity.getGPrice() : promoEntity.getPromoItemPrice());
        orderEntity.setOrderPrice(orderEntity.getItemPrice().multiply(new BigDecimal(orderInfoDTO.getShoppingCount())));
        return orderEntity;
    }
}
