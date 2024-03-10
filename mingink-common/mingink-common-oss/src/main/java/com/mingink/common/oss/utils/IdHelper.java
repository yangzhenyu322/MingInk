package com.mingink.common.oss.utils;

import cn.hutool.core.lang.UUID;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/3 23:08
 * @description
 */
public class IdHelper {
    private static final Integer UUIDLENGTH = 36;
    private static String UUIDREGEX = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";

    public IdHelper() {
    }

    public static String randomUUID() {
        String id = "";
        synchronized(id) {
            id = UUID.randomUUID().toString();
            return id;
        }
    }

    public static String simpleUUID() {
        return UUID.randomUUID().toString(true);
    }

    public static String fastUUID() {
        return UUID.fastUUID().toString();
    }

    public static String fastSimpleUUID() {
        return UUID.fastUUID().toString(true);
    }

    public static String getEmptyUUID() {
        return "00000000-0000-0000-0000-000000000000";
    }

    public static Boolean isUUID(String s) {
        return !s.equals("") && s.length() == UUIDLENGTH ? s.matches(UUIDREGEX) : false;
    }
}