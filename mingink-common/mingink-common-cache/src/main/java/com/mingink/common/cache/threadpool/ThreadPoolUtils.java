package com.mingink.common.cache.threadpool;

import java.util.concurrent.*;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/2 17:27
 * @description 线程工具类
 */
public class ThreadPoolUtils {
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(4,
            8,
            30,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(4096),
            new ThreadPoolExecutor.CallerRunsPolicy());
    /**
     * execute task in thread pool
     */
    public static void execute(Runnable command){
        executor.execute(command);
    }

    public static <T> Future<T> shumit(Callable<T> task){
        return executor.submit(task);
    }

    public static void shutdown(){
        if (executor != null){
            executor.shutdown();
        }
    }
}
