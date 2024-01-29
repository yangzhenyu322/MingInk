package com.minglink.common.core.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件处理工具
 * @Author: ZenSheep
 * @Date: 2024/1/29 20:57
 */
@Slf4j
public class FileUtils {
    /**
     * 获取文件名（不包含后缀）
     * @param fileName 元文件名（包含后缀）
     * @return
     */
    public static String getBaseName(String fileName) {
        return FilenameUtils.getBaseName(fileName);
    }

    /**
     * url文件路径转File对象，返回本地文件路径（方便后续删除缓存文件）
     * @param url 文件url路径
     * @param fileName 文件名
     * @return 文件本地路径
     * @throws IOException
     */
    public static String urlToFilePath(String url, String fileName) throws IOException {
        String tempFileName = "./temp/url/file/" + fileName;
        // 创建路径中的文件夹
        new File(tempFileName).getParentFile().mkdirs();
        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(tempFileName);
        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        while((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
        in.close();
        fileOutputStream.close();

        return tempFileName;
    }

    /**
     * 文件本地路径转File对象
     * @param pathName 本地文件路径
     * @return File对象
     */
    public static File filePathToFile(String pathName) {
        File file = new File(pathName);
        // 创建路径中的文件夹
        file.getParentFile().mkdirs();
        return file;
    }

    /**
     * 删除文件
     * @param file 文件
     * @return 是否删除成功：boolean
     */
    public static boolean deleteFile(File file) {
        boolean flag = false;
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }

        if (flag) {
            log.info("本地文件删除成功");
        } else {
            log.error("本地文件删除失败");
        }
        return flag;
    }


    /**
     * 保证文件名唯一
     * @param names 一般文件名：girl.png
     * @return
     */
    public static String[] getUniFileNames(String[] names) {
        int n = names.length;

        String[] baseNames = new String[n];
        String[] suffixTypes = new String[n];
        for (int i = 0; i < n; i++) {
            int lastDotIndex = names[i].lastIndexOf(".");
            baseNames[i] = names[i].substring(0, lastDotIndex);
            suffixTypes[i] = names[i].substring(lastDotIndex);
        }

        Map<String, Integer> index = new HashMap<String, Integer>();

        String[] res = new String[n];
        for (int i = 0; i < n; i++) {
            String name = baseNames[i];
            if (!index.containsKey(name)) {
                res[i] = name;
                index.put(name, 1);
            } else {
                int k = index.get(name);
                while (index.containsKey(addSuffix(name, k))) {
                    k++;
                }
                res[i] = addSuffix(name, k);
                index.put(name, k + 1);
                index.put(addSuffix(name, k), 1);
            }
        }

        // 对新文件列表添加文件类型后缀
        for (int i = 0; i < n; i++) {
            res[i] += suffixTypes[i];
        }

        return res;
    }

    /**
     * 添加括号后缀
     * @param name
     * @param k
     * @return
     */
    public static String addSuffix(String name, int k) {
        return name + "(" + k + ")";
    }
}
