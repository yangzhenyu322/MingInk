package com.mingink.system.service.impl;

import com.mingink.common.oss.utils.MinioUtil;
import com.mingink.system.service.IOSSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/9 22:13
 */
@Slf4j
@Service
public class OSSService implements IOSSService {
    @Autowired
    private MinioUtil minioUtil;

    @Override
    public String uploadFile(MultipartFile file, String customFilePath) {
        return minioUtil.upload(file, minioUtil.getBucketName(), customFilePath);
    }

    @Override
    public Boolean removeFile(String filePath) {
        return minioUtil.removeFile(filePath);
    }
}
