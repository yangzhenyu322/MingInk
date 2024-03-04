package com.mingink.article.utils;

import java.util.List;

/**
 * Gorse工具类
 * @Author: ZenSheep
 * @Date: 2024/3/4 21:12
 */
public class GorseUtils {
    public static String tagNamesToStr(List<String> tagNames) {
        StringBuilder tagNamesStr = new StringBuilder("[");
        for (int i = 0; i < tagNames.size(); i++) {
            tagNamesStr.append("\"").append(tagNames.get(i)).append("\"");
            if (i != tagNames.size() - 1) {
                tagNamesStr.append(",");
            }
        }
        tagNamesStr.append("]");

        return String.valueOf(tagNamesStr);
    }
}
