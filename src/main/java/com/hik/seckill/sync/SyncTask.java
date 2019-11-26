package com.hik.seckill.sync;

import org.springframework.stereotype.Component;

/**
 * Created by wangJinChang on 2019/11/20 17:06
 * 异步任务
 */
@Component
public class SyncTask {

    public SyncTask() {
    }

    public String syncTask() {
        /*for (int i = 1; i < 11; i++) {
            System.out.println(Thread.currentThread().getName() + " == " + DateUtil.formatDate(new Date()) + " 任务" + i);
        }*/
        return "success";
    }
}
