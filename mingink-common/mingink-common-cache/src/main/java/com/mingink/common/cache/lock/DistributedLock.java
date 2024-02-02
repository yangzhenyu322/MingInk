package com.mingink.common.cache.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/2 17:28
 * @description 分布式锁接口
 */
public interface DistributedLock {
    /**
     * @param waitTime 等待时间
     * @param leaseTime 持有时间
     * @param unit 时间单位
     * @return
     * @throws InterruptedException
     */
    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException;

    boolean tryLock(long waitTime,  TimeUnit unit) throws InterruptedException;

    boolean tryLock() throws InterruptedException;

    void lock(long leaseTime, TimeUnit unit);

    void unlock();

    boolean isLocked();

    boolean isHeldByThread(long threadId);

    boolean isHeldByCurrentThread();

}
