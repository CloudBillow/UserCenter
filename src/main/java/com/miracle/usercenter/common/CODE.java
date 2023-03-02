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

    /* ==================== Token ==================== */
    /**
     * Token过期
     */
    TOKEN_EXPIRED(10001, "Token过期"),
    /**
     * Token错误
     */
    TOKEN_ERROR(10002, "Token错误"),
    /**
     * Token无效
     */
    TOKEN_INVALID(10003, "Token无效"),

    /* ==================== 用户 ==================== */
    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(20001, "用户名或密码错误"),
    /**
     * 用户未登录
     */
    USER_NOT_LOGIN(20002, "未登录"),
    /**
     * 无权限
     */
    NO_PERMISSION(20003, "无权限"),
    /**
     * 用户名不能为空
     */
    USERNAME_NULL(20004, "用户名不能为空"),
    /**
     * 密码不能为空
     */
    PASSWORD_NULL(20005, "密码不能为空"),
    /**
     * 用户名长度必须在6-20位之间
     */
    USERNAME_LENGTH_ERROR(20006, "用户名长度必须在6-20位之间"),
    /**
     * 密码长度必须在6-20位之间
     */
    PASSWORD_LENGTH_ERROR(20007, "密码长度必须在6-20位之间"),
    /**
     * 密码必须包含大小写字母和数字
     */
    PASSWORD_FORMAT_ERROR(20008, "密码必须包含大小写字母和数字"),
    /**
     * 用户名已存在
     */
    USERNAME_EXIST(20009, "用户名已存在")

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