package com.miracle.usercenter.handler;

import com.miracle.usercenter.common.CODE;
import com.miracle.usercenter.common.Result;
import com.miracle.usercenter.common.UserCenterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义异常
     */
    @ExceptionHandler(UserCenterException.class)
    public Result<?> userCenterException(UserCenterException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 用户名或密码错误
     */
    @ExceptionHandler(BadCredentialsException.class)
    public Result<?> badCredentialsException() {
        return Result.fail(CODE.USERNAME_OR_PASSWORD_ERROR);
    }

    /**
     * 用户无权限
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> accessDeniedException() {
        return Result.fail(CODE.NO_PERMISSION);
    }

    /**
     * 全局异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> exception(Exception e, HttpServletRequest request) {
        log.error("接口{}异常", request.getRequestURI(), e);
        return Result.fail(CODE.FAIL);
    }
}