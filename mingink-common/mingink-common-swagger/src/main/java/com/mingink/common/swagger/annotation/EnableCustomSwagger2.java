package com.mingink.common.swagger.annotation;

import com.mingink.common.swagger.config.SwaggerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自定义 swagger 注解
 * @Author: ZenSheep
 * @Date: 2024/2/6 14:53
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ SwaggerConfiguration.class })
public @interface EnableCustomSwagger2 {

}