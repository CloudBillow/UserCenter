package com.miracle.usercenter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miracle.usercenter.common.CODE;
import com.miracle.usercenter.common.Result;
import com.miracle.usercenter.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户认证Handler
 *
 * @author XieYT
 * @since 2023/02/28 23:23
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.info("用户未登录:{}", authException.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        Result<String> result = Result.fail(
                CODE.USER_NOT_LOGIN.getCode(),
                authException.getMessage()
        );
        WebUtils.renderString(response, objectMapper.writeValueAsString(result));
    }
}
