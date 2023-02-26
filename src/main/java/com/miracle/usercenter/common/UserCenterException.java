package com.miracle.usercenter.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义异常
 *
 * @author XieYT
 * @since 2022/10/10 14:05
 */
@SuppressWarnings("unused")
@Getter
@AllArgsConstructor
public class UserCenterException extends RuntimeException {

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误消息
     */
    private String message;

    public UserCenterException(CODE codeEnum) {
        super(codeEnum.getMessage());
        init(codeEnum.getCode(), codeEnum.getMessage());
    }

    public UserCenterException(CODE codeEnum, Throwable cause) {
        super(codeEnum.getMessage(), cause);
        init(codeEnum.getCode(), codeEnum.getMessage());
    }

    public UserCenterException(CODE codeEnum, Exception cause) {
        super(codeEnum.getMessage(), cause);
        init(codeEnum.getCode(), codeEnum.getMessage());
    }

    public UserCenterException(CODE codeEnum, String message) {
        super(message);
        init(codeEnum.getCode(), message);
    }

    private void init(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

}