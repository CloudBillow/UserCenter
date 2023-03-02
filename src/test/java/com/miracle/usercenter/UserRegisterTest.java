package com.miracle.usercenter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miracle.usercenter.pojo.dto.UserDTO;
import com.miracle.usercenter.pojo.vo.TokenVO;
import com.miracle.usercenter.service.RegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户注册测试
 *
 * @author XieYT
 * @since 2023/03/02 23:29
 */
@SpringBootTest
public class UserRegisterTest {

    @Resource
    private RegisterService registerService;

    @Test
    public void testRegisterByUsernameAndPassword() throws JsonProcessingException {
        TokenVO token = registerService.registerByUsernameAndPassword(
                UserDTO.builder()
                        .username("miracle")
                        .password("Aa111111")
                        .build()
        );
        System.out.println("token = " + new ObjectMapper().writeValueAsString(token));
    }
}
