package com.miracle.usercenter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miracle.usercenter.common.CODE;
import com.miracle.usercenter.common.Result;
import com.miracle.usercenter.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限校验Handler
 *
 * @author XieYT
 * @since 2023/02/28 23:31
 */
@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.info("用户无权限:{}", accessDeniedException.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        Result<String> result = Result.fail(CODE.NO_PERMISSION);
        WebUtils.renderString(response, objectMapper.writeValueAsString(result));
    }
}
