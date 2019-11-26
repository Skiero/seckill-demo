package com.hik.seckill.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Created by wangJinChang on 2019/11/11 11:09
 * 定时任务配置类
 */
@Configuration
public class ScheduleConf {
    @Bean
    public TaskScheduler getTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix("springboot-task ==> " + Thread.currentThread().getName() + " ==> ");
        return threadPoolTaskScheduler;
    }
}
