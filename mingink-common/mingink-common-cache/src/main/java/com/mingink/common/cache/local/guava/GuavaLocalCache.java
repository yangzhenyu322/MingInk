package com.mingink.common.cache.local.guava;

import com.google.common.cache.Cache;
import com.mingink.common.cache.local.LocalCache;
import com.mingink.common.cache.local.factory.LocalGuavaCacheFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/2 17:38
 * @description
 */
@Component
@ConditionalOnProperty(name = "cache.type.local", havingValue = "guava")
public class GuavaLocalCache<K, V> implements LocalCache<K, V> {
    //本地缓存，基于Guava实现
    private final Cache<K, V> cache = LocalGuavaCacheFactory.getLocalCache();

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
