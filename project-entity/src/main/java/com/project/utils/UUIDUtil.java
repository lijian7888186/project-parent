package com.project.utils;

import java.util.UUID;

/**
 * describe:
 *
 * @author
 */
public class UUIDUtil {
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String string = uuid.toString().replace("-", "");
        return string;
    }
}
