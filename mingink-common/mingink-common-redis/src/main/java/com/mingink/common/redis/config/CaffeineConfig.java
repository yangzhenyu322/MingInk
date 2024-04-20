package com.mingink.common.redis.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ZenSheep
 * @Date: 2024/4/18 19:35
 */
@Slf4j
@Configuration
public class CaffeineConfig {

    @Bean
    public CacheManager caffeineCacheManager() {
        List<CaffeineCache> caffeineCaches = new ArrayList<>();

        // 可自行在 yml 或使用枚举设置多个缓存，不同名字的缓存的不同配置
        caffeineCaches.add(new CaffeineCache("defaultCache", defaultCache()));

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caffeineCaches);
        return cacheManager;
    }

    @Bean
    public Cache<Object, Object> defaultCache() {
        return Caffeine.newBuilder()
                // 缓存初始大小
                .initialCapacity(100)
                // 缓存上限
                .maximumSize(10_000)
                // PS: expireAfterWrite 和 expireAfterAccess 同时存在时，以 expireAfterWrite 为准
                // 最后一次写操作后经过指定时间过期
                .expireAfterWrite(5, TimeUnit.MINUTES)
                // 最后一次读或写操作后经过指定时间过期
//                .expireAfterAccess(1, TimeUnit.SECONDS)
                // 移除缓监听器
                .removalListener((key, val, cause) -> {
                    log.info("被移除的键：{}，原因：{}", key, cause);
                })
                // 详尽的统计信息，包括命中率、加载时间等
                .recordStats()
                .build();
    }
}