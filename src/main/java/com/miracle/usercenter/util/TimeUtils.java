package com.miracle.usercenter.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 时间工具类
 *
 * @author XieYT
 * @since 2023/02/26 18:50
 */
public class TimeUtils {

    /**
     * 获取十位时间戳
     *
     * @return 十位时间戳
     */
    public static int tenBitTimestamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 将十位时间戳转换为LocalDateTime
     *
     * @param tenBitTimestamp 十位时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByTenBitTimestamp(int tenBitTimestamp) {
        return LocalDateTime.ofEpochSecond(tenBitTimestamp, 0, ZoneOffset.ofHours(8));
    }
}
