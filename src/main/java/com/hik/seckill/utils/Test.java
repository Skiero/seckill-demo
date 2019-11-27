package com.hik.seckill.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.hik.seckill.model.vo.OrderInfoVO;
import com.hik.seckill.model.vo.ResultVO;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by wangJinChang on 2019/11/25 20:20
 * TODO
 */
public class Test {
    public static void main(String[] args) {
        String result = "{\"code\":\"0\",\"msg\":\"购买成功\",\"data\":null\"}}";
        ResultVO<OrderInfoVO> resultVO = JSON.parseObject(result, new TypeReference<ResultVO<OrderInfoVO>>() {
        });
        System.out.println(resultVO);

    }
}
