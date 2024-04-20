package com.mingink.common.cache.local;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/2 17:34
 * @description 本地缓存接口
 */
public interface LocalCache<K, V> {
    /**
     * 向缓存中添加数据
     * @param key 缓存的key
     * @param value 缓存的value
     */
    void put(K key, V value);

    /**
     * 根据key从缓存中查询数据
     * @param key 缓存的key
     * @return 缓存的value值
     */
    V getIfPresent(K key);

    /**
     * 移除缓存中的数据
     * @param key 缓存的key
     */
    void remove(K key);
}
