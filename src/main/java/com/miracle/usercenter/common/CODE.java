package com.miracle.usercenter.common;

import lombok.Getter;

/**
 * 业务码
 *
 * @author Miracle
 * @since 2022/8/26 19:34
 */
@Getter
public enum CODE {

    /* ==================== 系统状态码 ==================== */
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 失败
     */
    FAIL(-1, "失败"),
    /**
     * 请求的资源不存在
     */
    NOT_FOUND(404, "请求的资源不存在"),
    /**
     * 服务器内部错误
     */
    INTERNAL_ERROR(500, "服务器内部错误"),
    /**
     * 请求参数校验异常
     */
    PARAMETER_EXCEPTION(501, "请求参数校验异常"),

    /* ==================== 用户 ==================== */
    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(10001, "用户名或密码错误"),
    /**
     * 用户未登录
     */
    USER_NOT_LOGIN(10002, "用户未登录"),
    /**
     * Token错误
     */
    TOKEN_ERROR(10003, "token错误"),

    ;

    /**
     * 业务状态码
     */
    private final Integer code;

    /**
     * 业务状态描述
     */
    private final String message;

    /**
     * 全参构造器
     *
     * @param code    业务状态码
     * @param message 业务状态描述
     */
    CODE(int code, String message) {
        this.code = code;
        this.message = message;
    }
}