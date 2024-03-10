package com.mingink.system.controller;

import com.mingink.common.core.domain.R;
import com.mingink.common.oss.utils.MinioUtil;
import com.mingink.system.service.IOSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * @Author: ZenSheep
 * @Date: 2024/3/8 22:06
 */
@RestController
@RequestMapping("/oss")
public class OSSController {
    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private IOSSService oSSService;

    /**
     * 创建桶
     *
     * @param bucketName
     * @return Boolean
     */
    @PostMapping("/createBucket")
    public R<Boolean> createBucket(@RequestParam String bucketName) {
        if (minioUtil.existBucket(bucketName)) return R.fail("已存在");
        return minioUtil.makeBucket(bucketName) ? R.ok(true) : R.fail("创建失败");
    }

    /**
     * 删除桶
     *
     * @param bucketName
     * @return Boolean
     */
    @DeleteMapping("/removeBucketByName")
    public R<Boolean> removeBucketByName(@RequestParam String bucketName) {
        if (!minioUtil.existBucket(bucketName)) return R.fail("未查到该桶");
        return minioUtil.removeBucket(bucketName) ? R.ok(true) : R.fail("删除失败");
    }

    /**
     * 单文件上传
     *
     * @param file
     * @param bucketName
     * @return String 文件路径
     */
    @PostMapping("/uploadSingleFileByFileOrBucketName")
    public R<String> uploadSingleFileByFileOrBucketName(MultipartFile file,  String bucketName) {
        return (bucketName.equals("") || minioUtil.existBucket(bucketName)) ? R.ok(minioUtil.upload(file, bucketName)) : R.fail("该桶不存在");
    }

    /**
     * 批量文件上传
     *
     * @param files
     * @param bucketName
     * @return Map 文件文件路径集合
     */
    @PostMapping("/uploadClusterFileByFileOrBucketName")
    public R<Map<String, String>> uploadClusterFileByFileOrBucketName(MultipartFile[] files, @RequestParam String bucketName) {
        return (bucketName.equals("") || minioUtil.existBucket(bucketName)) ? R.ok(minioUtil.upload(files, bucketName)) : R.fail("该桶不存在");
    }

    /**
     * 单文件下载（因为存在多级目录，通过文件路径）
     *
     * @param filePath
     * @param response
     * @param request
     * @return Boolean
     */
    @PostMapping("/downloadSingleFileByFilePath")
    public void downloadSingleFileByFilePath(@RequestParam String filePath, HttpServletResponse response, HttpServletRequest request) {
        minioUtil.download(response, request, filePath);
    }

    /**
     * 批量文件下载（因为存在多级目录，通过文件路径）
     *
     * @param filePaths
     * @param response  可为空
     * @param response
     * @param request
     */
    @PostMapping("/downloadClusterFileByFilePaths")
    public void downloadClusterFileByFilePaths(@RequestParam List<String> filePaths, @RequestParam String zipName, HttpServletResponse response, HttpServletRequest request) {
        minioUtil.download(response, request, filePaths, zipName);
    }

    /**
     * 单文件删除（多级目录，使用文件路径，mingink/2024/03/08/girl07.png）
     *
     * @param filePath
     * @return Boolean
     */
    @DeleteMapping("/removeSingleFileByFilePath")
    public R<Boolean> removeSingleFileByFilePath(@RequestParam String filePath) {
        return minioUtil.removeFile(filePath) ? R.ok(true) : R.fail("删除失败");
    }

    /**
     * 文件批量删除（多级目录，使用文件路径）
     *
     * @param filePaths
     * @return Map
     */
    @DeleteMapping("/removeClusterFileByFilePaths")
    public R<Map<String, String>> removeClusterFileByFilePaths(@RequestParam List<String> filePaths) {
        return R.ok(minioUtil.removeFile(filePaths));
    }

    /**
     * 单文件上传
     * @param file 文件
     * @param customFilePath 自定义文件路径，如zensheep/avatar/
     * @return 文件url, 如 http://223.82.75.76:9100/mingink/zensheep/avatar/2024/03/09/9d3f7e46-001f-4fba-8f74-d76bfdaf0f5b.jfif
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestPart(value = "file") MultipartFile file, @RequestParam("customFilePath") String customFilePath) {
        return oSSService.uploadFile(file, customFilePath);
    }

    /**
     * 单文件删除
     * @param filePath 文件路径，由 bucketName/xx/fileNawe.xx 组成, 如 mingink/2024/03/09/9d3f7e46-001f-4fba-8f74-d76bfdaf0f5b.jfif
     * @return
     */
    @DeleteMapping("/filePath/{filePath}")
    public Boolean removeFile(@PathVariable("filePath") String filePath) {
        return oSSService.removeFile(filePath);
    }
}
