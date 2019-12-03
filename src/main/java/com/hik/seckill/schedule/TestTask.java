package com.hik.seckill.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.hik.seckill.model.param.OrderInfoParam;
import com.hik.seckill.model.vo.OrderInfoVO;
import com.hik.seckill.model.vo.ResultVO;
import com.hik.seckill.utils.DateUtil;
import com.hik.seckill.utils.HttpClientSSLUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by wangJinChang on 2019/11/9 17:31
 * spring boot定时任务
 */
@Component
@Slf4j
public class TestTask {

    //定时任务,1分钟执行一次。执行频率通过cron表达式确定
//    @Scheduled(cron = "0/60 * * * * ?")
    public void test() {
        System.out.println("*** system time is " + DateUtil.formatDate(new Date()) + " ***");
        String url = "http://127.0.0.1:8082" + "/goods/order";
        OrderInfoParam param = new OrderInfoParam();
        param.setAddress("浙江杭州滨江");
        param.setGoodsId(5);
        param.setGoodsName("炒河粉");
        param.setGoodsType("宵夜烧烤");
        param.setShoppingCount(20);
        String jsonText = JSON.toJSONString(param);
        HashMap<String, String> header = Maps.newHashMap();
        header.put("Content-Type", "application/json");
        header.put("Cookie", "user-login=750a588ada5847f9a9277495576d33a1");
        /*System.out.println("url ==> " + url);
        System.out.println("jsonText ==> " + jsonText);
        System.out.println("header ==> " + JSON.toJSONString(header));*/
        for (int i = 0; i < 50; i++) {
            try {
                String result = HttpClientSSLUtil.doPostToStringSecurity(url, jsonText, header);
                ResultVO<OrderInfoVO> resultVO = JSON.parseObject(result, new TypeReference<ResultVO<OrderInfoVO>>() {
                });
                if (Objects.isNull(resultVO)) {
                    log.info("TestTask resultVO is null!");
                }
            } catch (Exception e) {
                log.error("TestTask http request has error , msg is {} , caused by {} ", e.getMessage(), e.getCause());
            }
        }
    }
}
