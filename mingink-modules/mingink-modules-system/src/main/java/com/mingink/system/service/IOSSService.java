package com.mingink.system.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/9 22:12
 */
public interface IOSSService {
    String uploadFile(MultipartFile file, String customFilePath);

    Boolean removeFile(String filePath);
}
