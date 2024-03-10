package com.mingink.system.api;

import com.mingink.system.api.config.DefaultFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/9 21:53
 */
@FeignClient(name = "mingink-system", contextId = "remote-oss", configuration = DefaultFeignConfiguration.class)
public interface RemoteOSSService {
    @PostMapping(value = "/oss/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestPart(value = "file") MultipartFile file, @RequestParam("customFilePath") String customFilePath);

    @DeleteMapping("/oss/filePath/{filePath}")
    public Boolean removeFile(@PathVariable("filePath") String filePath);
}
