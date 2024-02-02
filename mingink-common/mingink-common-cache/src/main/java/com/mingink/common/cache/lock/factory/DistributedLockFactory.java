package com.mingink.common.cache.lock.factory;

import com.mingink.common.cache.lock.DistributedLock;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/2 17:30
 * @description 分布式锁工厂接口
 */
public interface DistributedLockFactory {
    /**
     * 根据key获取分布式锁
     */
    DistributedLock getDistributedLock(String key);
}
