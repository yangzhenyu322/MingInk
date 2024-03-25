package com.mingink.common.cache.local.factory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/2 17:36
 * @description 基于caffeine的本地缓存工厂
 */
public class LocalCaffeineCacheFactory {

    public static <K, V> Cache<K, V> getLocalCache(){
        return Caffeine.newBuilder()
                .initialCapacity(200) //初始数量
                .maximumSize(300) //最大条数
                //expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准
                //最后一次写操作后经过指定时间过期
                .expireAfterWrite(300, TimeUnit.SECONDS)
                .removalListener((key,val,removalCause)->{}) //监听缓存被移除
                .recordStats() //记录命中
                .build();
    }
    public static <K, V> Cache<K, V> getLocalCache(long duration){
        return Caffeine.newBuilder()
                .initialCapacity(200) //初始数量
                .maximumSize(300) //最大条数
                //expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准
                //最后一次写操作后经过指定时间过期
                .expireAfterWrite(duration, TimeUnit.SECONDS)
                .removalListener((key,val,removalCause)->{}) //监听缓存被移除
                .recordStats() //记录命中
                .build();
    }

    public static <K, V> Cache<K, V> getLocalCache(int initialCapacity, long duration){
        return Caffeine.newBuilder()
                .initialCapacity(initialCapacity) //初始数量
                .maximumSize(300) //最大条数
                //expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准
                //最后一次写操作后经过指定时间过期
                .expireAfterWrite(duration, TimeUnit.SECONDS)
                .removalListener((key,val,removalCause)->{}) //监听缓存被移除
                .recordStats() //记录命中
                .build();
    }
}
