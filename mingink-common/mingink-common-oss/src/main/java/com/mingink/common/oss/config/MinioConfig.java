package com.mingink.common.oss.config;

import com.mingink.common.oss.utils.MinioUtil;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/3 21:58
 * @description minio配置类
 */
@Data
@Configuration
@Import({ MinioUtil.class })
public class MinioConfig {
    // 访问地址
    @Value("${minio.url}")
    private String url;
    // 用户ID，用于标识你的账户
    @Value("${minio.accessKey}")
    private String accessKey;
    // 账户密码
    @Value("${minio.secretKey}")
    private String secretKey;

    // 默认存储桶
    @Value("${minio.bucketName}")
    private String bucketName;

    @Bean
    public MinioClient getMinioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
        return minioClient;
    }
}