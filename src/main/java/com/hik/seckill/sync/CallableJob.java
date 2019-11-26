package com.hik.seckill.sync;

import com.hik.seckill.constant.CommonConstant;
import com.hik.seckill.enums.ShoppingCartResponseEnum;
import com.hik.seckill.model.vo.CartInfoVO;
import com.hik.seckill.sync.service.ShoppingCartService;
import com.hik.seckill.utils.SpringBeanContextUtil;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Callable;

/**
 * Created by wangJinChang on 2019/11/20 17:02
 * 实现Callable的异步任务
 */
public class CallableJob implements Callable {

    private Integer mark;

    private CartInfoVO cartInfoVO;

    private Integer userId;

    public CallableJob() {
    }

    public CallableJob(@NotNull(message = "标记不能为空") Integer mark,
                       CartInfoVO cartInfoVO,
                       @NotNull(message = "用户ID不能为空") Integer userId) {
        this.mark = mark;
        this.cartInfoVO = cartInfoVO;
        this.userId = userId;
    }

    @Override
    public ShoppingCartResponseEnum call() throws Exception {
        ShoppingCartService service = SpringBeanContextUtil.getBean(ShoppingCartService.class);

        if (mark.equals(CommonConstant.ADD_SHOPPING_CART)) {
            return service.addShoppingCartToDataBase(cartInfoVO, userId);
        } else if (mark.equals(CommonConstant.MODIFY_SHOPPING_CART)) {
            return service.modifyShoppingCartFromDataBase(cartInfoVO, userId);
        } else {
            return service.removeShoppingCartFromDataBase(cartInfoVO, userId);
        }
    }
}
