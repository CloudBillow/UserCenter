package com.miracle.usercenter;

import com.miracle.usercenter.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT测试类
 *
 * @author XieYT
 * @since 2023/02/26 20:43
 */
@SpringBootTest
public class JwtTest {

    @Test
    public void testJwtEncode() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "test");
        map.put("userId", 123);
        String token = JwtUtil.createToken("login", map);
        System.out.println("token = " + token);

        Jws<Claims> claimsJws = JwtUtil.parseToken(token);
        System.out.println("claimsJws = " + claimsJws);
    }
}
