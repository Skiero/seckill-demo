package com.hik.seckill.sync;

import com.hik.seckill.error.CommonException;
import com.hik.seckill.sync.service.OrderService;
import com.hik.seckill.utils.SpringBeanContextUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by wangJinChang on 2019/11/20 17:12
 * 实现runnable的异步任务
 */
@Slf4j
public class RunnableJob implements Runnable {

    private Integer goodId;

    private Integer userId;

    public RunnableJob() {
    }

    public RunnableJob(Integer goodId, Integer userId) {
        this.goodId = goodId;
        this.userId = userId;
    }

    @Override
    public void run() {
        OrderService service = SpringBeanContextUtil.getBean(OrderService.class);
        try {
            service.removeShoppingCart(goodId, userId);
        } catch (CommonException e) {
            log.error("OrderService removeShoppingCart from database has exception , code is {} , message is {} ", e.getErrCode(), e.getErrMsg());
        }
    }
}
