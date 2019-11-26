package com.hik.seckill.schedule;

import com.hik.seckill.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by wangJinChang on 2019/11/9 17:31
 * TODO
 */
@Component
public class TestTask {
    @Scheduled(cron = "0/600 * * * * ?")
    public void test() {
        System.out.println(Thread.currentThread().getName() + "  " + DateUtil.formatDate(new Date()));
        System.out.println(Thread.currentThread().getName() + "  " + DateUtil.formatDateV1(new Date()));
    }
}
