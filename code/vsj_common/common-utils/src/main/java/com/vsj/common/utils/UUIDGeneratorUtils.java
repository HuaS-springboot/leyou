package com.vsj.common.utils;

import java.util.UUID;

public class UUIDGeneratorUtils {

    private UUIDGeneratorUtils() {
    }

    /**
     * 获取32位UUID
     *
     * @return
     */
    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }

    public static String getUUIDUpper() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static String getUUIDLower() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
