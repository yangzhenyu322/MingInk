package com.mingink.common.cache.local.factory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/2 17:35
 * @description 基于Guava的本地缓存工厂
 */
public class LocalGuavaCacheFactory {
    /**
     * concurrencyLevel：同时写缓存的线程数,一般设置并发级别为cpu核心数
     * initialCapacity：设置缓存容器的初始容量
     * maximumSize：设置缓存最大容量
     * expireAfterWrite：设置过期策略
     */
    public static <K, V> Cache<K, V> getLocalCache(){
        return CacheBuilder.newBuilder().initialCapacity(200).concurrencyLevel(5).expireAfterWrite(300, TimeUnit.SECONDS).build();
    }

    public static <K, V> Cache<K, V> getLocalCache(long duration){
        return CacheBuilder.newBuilder().initialCapacity(200).concurrencyLevel(5).expireAfterWrite(duration, TimeUnit.SECONDS).build();
    }

    public static <K, V> Cache<K, V> getLocalCache(int initialCapacity, long duration){
        return CacheBuilder.newBuilder().initialCapacity(initialCapacity).concurrencyLevel(5).expireAfterWrite(duration, TimeUnit.SECONDS).build();
    }

}
