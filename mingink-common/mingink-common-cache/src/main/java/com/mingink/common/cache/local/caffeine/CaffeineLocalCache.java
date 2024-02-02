package com.mingink.common.cache.local.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.mingink.common.cache.local.LocalCache;
import com.mingink.common.cache.local.factory.LocalCaffeineCacheFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/2 17:37
 * @description 基于caffeine实现本地缓存
 */
@Component
@ConditionalOnProperty(name = "cache.type.local", havingValue = "caffeine")
public class CaffeineLocalCache<K, V> implements LocalCache<K, V> {

    //本地缓存，基于Caffeine实现
    private final Cache<K, V> cache = LocalCaffeineCacheFactory.getLocalCache();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public V getIfPresent(K key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void remove(K key) {
        cache.invalidate(key);
    }
}
