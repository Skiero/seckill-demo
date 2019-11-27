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

    /**
     * 执行Runnable任务,无返回结果
     *
     * @param task Runnable任务
     */
    public static void submit(Runnable task) {
        threadPoolExecutor.submit(task);
    }

    /**
     * 执行Callable任务,有返回结果
     *
     * @param task Callable任务
     * @param <T>  泛型
     * @return 执行结果
     */
    public static <T> Future<T> submit(Callable<T> task) {
        return threadPoolExecutor.submit(task);
    }
}
