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

    @Test
    public void testExp() {
        String token = "MIRACLE#eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbiIsIm5iZiI6MTY3NzY2MDI4OCwiZXhwIjoxNjc3NjYwMzE4LCJ1c2VySWQiOjEyMywiaWF0IjoxNjc3NjYwMjg4LCJqdGkiOiJXZWQgTWFyIDAxIDE2OjQ0OjQ4IENTVCAyMDIzIiwidXNlcm5hbWUiOiJ0ZXN0In0.Y5fdxngS8fq9B-ujF_BemMGxbHPMq5QM-DkQgfQJnZY";
        Boolean tokenExpired = JwtUtil.checkToken(token);

        System.out.println("tokenExpired = " + tokenExpired);
    }
}
