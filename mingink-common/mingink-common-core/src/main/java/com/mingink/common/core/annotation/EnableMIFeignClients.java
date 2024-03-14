package com.mingink.common.core.annotation;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

import java.lang.annotation.*;

/**
 * 自定义feign注解: Enable MingInk Feign Client
 * 添加basePackages路径
 * @Author: ZenSheep
 * @Date: 2024/1/29 20:25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableFeignClients
public @interface EnableMIFeignClients {
    String[] value() default {};

    String[] basePackages() default { "com.mingink" };

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
