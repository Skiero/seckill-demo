package com.hik.seckill.sync;

import java.util.concurrent.*;

/**
 * Created by wangJinChang on 2019/11/20 16:41
 * 异步任务执行器
 */
public class Executor {
    private static final int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static final int maximumPoolSize = corePoolSize * 2;
    private static final long keepAliveTime = 10;
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));

    public static void submit(Runnable task) {
        threadPoolExecutor.submit(task);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return threadPoolExecutor.submit(task);
    }
}
