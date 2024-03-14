package com.mingink.common.swagger.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 配置
 * @Author: ZenSheep
 * @Date: 2024/2/6 14:23
 */
@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("爱荧开放平台（测试版）")
                        .version("1.0.0")
                        .description("荧墨轩提供的Swagger接口文档，供开发人员参阅")
                        .license(new License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                );
    }
}
