package com.mingink.common.oss;

import com.mingink.common.oss.model.ObjectItem;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/4 15:40
 * @description minio服务接口
 */
public interface MinioService {

    String createFilePath(String fileName);
    String getFileNameByPath(String filePath);
    List<ObjectItem> queryFileListInBucket(String bucketName);
    boolean isContainInBucketByFile(String fileName, String bucketName);
    String upload(MultipartFile file, String bucketName);
    String upload(MultipartFile file);
    Map<String, String> upload(MultipartFile[] file, String bucketName);
    void download(HttpServletResponse response, HttpServletRequest request, String filePath);
    void download(HttpServletResponse response, HttpServletRequest request, List<String> filePaths, String zipName);
    boolean removeFile(String filePath);
    Map<String, String> removeFile(List<String> filePaths);
}
