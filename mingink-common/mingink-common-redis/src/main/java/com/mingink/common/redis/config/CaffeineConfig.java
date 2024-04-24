package com.mingink.common.redis.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Author: ZenSheep
 * @Date: 2024/4/18 19:35
 */
@Slf4j
@Configuration
public class CaffeineConfig {
    /**
     * cache 使用
     * 1、@Cacheable: 根据cacheNames+key查询缓存 => get
     *    在执行本方法之前回去向缓存中获取一次数据。如果获取到就直接将此缓存值放回本方法的返回值中；未能获取到就执行本方法，将本方法的结果放入缓存中。这样下次的在执行本方法就用缓存使用了
     * 2、@CachePut: 根据cacheNames+key增加缓存的，遇到同名就覆盖 => post、put
     *    将方法的返回值，放进指定的缓存中。所以本注解具备对缓存的新增和修改（覆盖）的功能
     * 3、@CacheEvict: 根据cacheNames+key删除缓存的 => delete
     *    将满足key的缓存删除，对该方法的返回值不会做任何的处理。但是这个删除默认是在方法结束之后进行的
     * 4、@Caching：用于组合前三个注解
     *
     * 测试
     * 重启服务会清除缓存
     * 不同的服务访问同一个缓存及相同的key，获取的访问值不同 => 缓存是不会跨服务的，只存在于服务进程所在的 jvm
     *
     * @return
     */

    // 方式一：通用缓存
    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                // 缓存初始大小
                .initialCapacity(100)
                // 缓存上限
                .maximumSize(10_000)
                // PS: expireAfterWrite 和 expireAfterAccess 同时存在时，以 expireAfterWrite 为准
                // 最后一次写操作后经过指定时间过期
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // 最后一次读或写操作后经过指定时间过期
//                .expireAfterAccess(10, TimeUnit.MINUTES)
                // 移除缓监听器
                .removalListener((key, val, cause) -> {
                    log.info("defaultCache被移除的键：{}，原因：{}", key, cause);
                })
                // 详尽的统计信息，包括命中率、加载时间等
                .recordStats();
    }

    // 方式二：个性换枚举缓存
//    @Bean
//    public CacheManager caffeineCacheManager() {
//        List<CaffeineCache> caffeineCaches = new ArrayList<>();
//
//        // 可自行在 yml 或使用枚举设置多个缓存，不同名字的缓存的不同配置
//        caffeineCaches.add(new CaffeineCache("defaultCache", defaultCache()));
//        caffeineCaches.add(new CaffeineCache("goodsCache", defaultCache()));
//
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        cacheManager.setCaches(caffeineCaches);
//        return cacheManager;
//    }
//
//    @Bean
//    public Cache<Object, Object> defaultCache() {
//        return Caffeine.newBuilder()
//                // 缓存初始大小
//                .initialCapacity(100)
//                // 缓存上限
//                .maximumSize(10_000)
//                // PS: expireAfterWrite 和 expireAfterAccess 同时存在时，以 expireAfterWrite 为准
//                // 最后一次写操作后经过指定时间过期
//                .expireAfterWrite(5, TimeUnit.MINUTES)
//                // 移除缓监听器
//                .removalListener((key, val, cause) -> {
//                    log.info("defaultCache被移除的键：{}，原因：{}", key, cause);
//                })
//                // 详尽的统计信息，包括命中率、加载时间等
//                .recordStats()
//                .build();
//    }
//
//    @Bean
//    public Cache<Object, Object> goodsCache() {
//        return Caffeine.newBuilder()
//                // 缓存初始大小
//                .initialCapacity(100)
//                // 缓存上限
//                .maximumSize(10_000)
//                // PS: expireAfterWrite 和 expireAfterAccess 同时存在时，以 expireAfterWrite 为准
//                // 最后一次写操作后经过指定时间过期
//                .expireAfterWrite(5, TimeUnit.MINUTES)
//                // 移除缓监听器
//                .removalListener((key, val, cause) -> {
//                    log.info("orderCache被移除的键：{}，原因：{}", key, cause);
//                })
//                // 详尽的统计信息，包括命中率、加载时间等
//                .recordStats()
//                .build();
//    }
}