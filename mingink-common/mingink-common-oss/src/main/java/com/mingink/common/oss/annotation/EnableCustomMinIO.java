package com.mingink.common.oss.annotation;

import com.mingink.common.oss.config.MinioConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自定义 MinIO 注解
 * @Author: ZenSheep
 * @Date: 2024/3/8 21:15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ MinioConfig.class })
public @interface EnableCustomMinIO {

}
