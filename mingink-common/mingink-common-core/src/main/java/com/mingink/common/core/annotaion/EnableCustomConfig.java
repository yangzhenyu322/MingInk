package com.mingink.common.core.annotaion;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * 自定义一般配置注解
 * 自动扫描Mapper包
 * @Author: ZenSheep
 * @Date: 2024/1/29 20:23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
// 通过aop框架暴露该代理对象，AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
//@MapperScan("com.mingink.**.mapper")
// 开启线程异步执行
@EnableAsync
// 自动加载类
//@Import({ ApplicationConfig.class, FeignAutoConfiguration.class })
public @interface EnableCustomConfig {

}
