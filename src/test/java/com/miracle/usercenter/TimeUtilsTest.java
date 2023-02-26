package com.miracle.usercenter;

import com.miracle.usercenter.util.TimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类测试
 *
 * @author XieYT
 * @since 2023/02/26 18:54
 */
@SpringBootTest
public class TimeUtilsTest {

    @Test
    public void testTenBitTimestamp() {
        int timestamp = TimeUtils.tenBitTimestamp();
        System.out.println(timestamp);

        LocalDateTime localDateTime = TimeUtils.getLocalDateTimeByTenBitTimestamp(timestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = localDateTime.format(formatter);
        System.out.println(format);
    }
}
