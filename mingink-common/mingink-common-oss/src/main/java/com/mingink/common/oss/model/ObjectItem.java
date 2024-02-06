package com.mingink.common.oss.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/3 23:24
 * @description
 */
@Data
@Accessors(chain = true)
public class ObjectItem {

    private String objectName;

    private String size;

    @Override
    public String toString() {
        return "ObjectItem{" + "名称='" + objectName + '\'' + ", 大小='" + size + '\'' + '}';
    }
}
