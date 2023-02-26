package com.miracle.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密测试类
 *
 * @author XieYT
 * @since 2023/02/26 19:52
 */
@SpringBootTest
public class BCryptPasswordEncoderTest {

    @Test
    public void testBCryptPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("1234");
        System.out.println("encode = " + encode);
        boolean matches = passwordEncoder.matches(
                "1234", encode
        );
        System.out.println("matches = " + matches);
    }
}
