package com.hik.seckill.utils;

import java.util.*;

/**
 * Created by wangJinChang on 2019/11/25 20:20
 * 测试
 */
public class Test {
    public static void main(String[] args) {
        Timer timer = new Timer();
        System.out.println(DateUtil.formatDate(new Date()));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(DateUtil.formatDate(new Date()));
                System.out.println("我只是来测试定时任务的");
            }
        }, 1000 * 60);
    }
}
