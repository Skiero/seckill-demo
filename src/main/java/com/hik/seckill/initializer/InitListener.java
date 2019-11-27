package com.hik.seckill.initializer;

import com.hik.seckill.utils.DateUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by wangJinChang on 2019/11/27 20:41
 * Spring Boot项目启动时初始化程序
 */
@Component
public class InitListener implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("********************************************");
        System.out.println("************欢迎使用 spring boot ************");
        System.out.println("**** system time is " + DateUtil.formatDate(new Date()) + " ****");
        System.out.println("********************************************");
    }
}
