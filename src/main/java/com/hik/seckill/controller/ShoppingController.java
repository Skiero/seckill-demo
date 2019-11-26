package com.hik.seckill.controller;

import com.hik.seckill.error.CommonException;
import com.hik.seckill.model.dto.CartInfoDTO;
import com.hik.seckill.enums.EmBusinessError;
import com.hik.seckill.model.dto.OrderInfoDTO;
import com.hik.seckill.model.param.CartInfoParam;
import com.hik.seckill.model.param.OrderInfoParam;
import com.hik.seckill.model.vo.CartInfoVO;
import com.hik.seckill.model.vo.OrderInfoVO;
import com.hik.seckill.model.vo.PageVO;
import com.hik.seckill.model.vo.ResultVO;
import com.hik.seckill.service.IShoppingService;
import com.hik.seckill.utils.CasUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by wangJinChang on 2019/11/23 17:02
 * 购买支付控制器层
 */
@RestController
@RequestMapping("/goods")
@Api(tags = "购买支付控制器层")
@Validated
@Slf4j
public class ShoppingController {
    @Autowired
    private IShoppingService shoppingService;

    @PostMapping("/shoppingCart")
    @ApiOperation(value = "增加购物车", notes = "增加购物车")
    public ResultVO addShoppingCart(@Valid @RequestBody CartInfoParam cartInfoParam) {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("ShoppingController addShoppingCart error code is {} , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        CartInfoDTO cartInfoDTO = new CartInfoDTO();
        BeanUtils.copyProperties(cartInfoParam, cartInfoDTO);
        PageVO<CartInfoVO> pageVO;
        try {
            pageVO = shoppingService.addShoppingCart(cartInfoDTO, optional.get());
        } catch (CommonException e) {
            return ResultVO.error(e.getErrCode(), e.getMessage());
        }
        return ResultVO.success("添加购物车成功", pageVO);
    }

    @RequestMapping(value = "/shoppingCart/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "删除购物车信息", notes = "删除购物车信息")
    public ResultVO removeShoppingCart(@PathVariable(value = "id") Integer id) {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("ShoppingController addShoppingCart error code is {} , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        CartInfoDTO cartInfoDTO = new CartInfoDTO();
        cartInfoDTO.setId(id);
        PageVO<CartInfoVO> cartInfoVOPageVO;
        try {
            cartInfoVOPageVO = shoppingService.removeCart(cartInfoDTO, optional.get());
        } catch (CommonException e) {
            log.error("ShoppingController removeShoppingCart has error , error code is {} , error message is {} ", EmBusinessError.SHOPPING_CART_SERVICE_ERROR.getErrCode(), e.getErrMsg());
            return ResultVO.error(e.getErrCode(), e.getErrMsg());
        }
        return ResultVO.success("删除成功", cartInfoVOPageVO);
    }

    @RequestMapping(value = "/shoppingCart/{id}/{count}", method = RequestMethod.GET)
    @ApiOperation(value = "修改购物车信息", notes = "修改购物车中的商品数量")
    public ResultVO modifyShoppingCart(@PathVariable(value = "id") Integer id,
                                       @PathVariable(value = "count") Integer count) {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("ShoppingController addShoppingCart error code is {} , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        CartInfoDTO cartInfoDTO = new CartInfoDTO();
        cartInfoDTO.setId(id);
        cartInfoDTO.setShoppingCount(count);
        PageVO<CartInfoVO> cartInfoVOPageVO;
        try {
            cartInfoVOPageVO = shoppingService.modifyShoppingCart(cartInfoDTO, optional.get());
        } catch (CommonException e) {
            log.error("ShoppingController modifyShoppingCart has error , error code is {} , error message is {} ", EmBusinessError.SHOPPING_CART_SERVICE_ERROR.getErrCode(), e.getErrMsg());
            return ResultVO.error(e.getErrCode(), e.getErrMsg());
        }
        return ResultVO.success("获取成功", cartInfoVOPageVO);
    }

    @RequestMapping(value = "/shoppingCart", method = RequestMethod.GET)
    @ApiOperation(value = "获取购物车信息", notes = "获取购物车信息")
    public ResultVO<PageVO<CartInfoVO>> getShoppingCart() {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("ShoppingController addShoppingCart error code is {} , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        PageVO<CartInfoVO> shoppingCart = shoppingService.getShoppingCart(optional.get());
        return ResultVO.success("获取成功", shoppingCart);
    }

    @PostMapping("/order")
    @ApiOperation(value = "下订单", notes = "下订单")
    public ResultVO addOrder(@Validated @RequestBody OrderInfoParam orderInfoParam) {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("ShoppingController addOrder error code is {} , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        BeanUtils.copyProperties(orderInfoParam, orderInfoDTO);
        OrderInfoVO orderInfoVO;
        try {
            orderInfoVO = shoppingService.addOrderInfo(orderInfoDTO, optional.get());
        } catch (CommonException e) {
            log.error("ShoppingController addOrder has error , error code is {} , error message is {} ", EmBusinessError.SHOPPING_CART_SERVICE_ERROR.getErrCode(), e.getErrMsg());
            return ResultVO.error(e.getErrCode(), e.getErrMsg());
        }
        return ResultVO.success("购买成功", orderInfoVO);
    }

    @GetMapping("/order")
    @ApiOperation(value = "查订单", notes = "查订单,若ID为空,则查询当前用户的所有订单信息,否则按照ID查询")
    public ResultVO getOrder(@RequestParam("ids") String ids) {
        Optional<Integer> optional = CasUtil.getUserId();
        if (!optional.isPresent()) {
            log.debug("ShoppingController addOrder error code is {} , message is {} ", EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
            return ResultVO.error(EmBusinessError.USER_NOT_LOGIN.getErrCode(), EmBusinessError.USER_NOT_LOGIN.getErrMsg());
        }
        PageVO<OrderInfoVO> orderInfo = shoppingService.getOrderInfo(ids, optional.get());
        return ResultVO.success("获取成功", orderInfo);
    }

}
