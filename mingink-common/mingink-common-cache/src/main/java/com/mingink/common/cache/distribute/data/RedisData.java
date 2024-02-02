package com.mingink.common.cache.distribute.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/2 17:24
 * @description 缓存到Redis中的数据，主要配合使用数据的逻辑过期
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisData {
    //实际业务数据
    private Object data;
    //过期时间点
    private LocalDateTime expireTime;
}
