package com.minglink.common.core.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus配置
 * @Author: ZenSheep
 * @Date: 2024/1/29 20:27
 */
@Configuration
public class MyBatisPlusConfig {
    /**
     * 分页插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mi = new MybatisPlusInterceptor();
        mi.addInnerInterceptor(new PaginationInnerInterceptor());

        return mi;
    }
}
