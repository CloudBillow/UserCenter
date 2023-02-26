package com.miracle.usercenter.common;

/**
 * TODO
 *
 * @author XieYT
 * @since 2023/02/26 17:47
 */

import lombok.Data;

/**
 * 统一返回结果封装
 *
 * @author Miracle
 * @since 2022/8/26 19:26
 */
@Data
@SuppressWarnings("unused")
public class Result<T> {

    /**
     * 业务码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public Result() {

    }

    public void setResultCode(CODE resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 成功（无返回数据）
     *
     * @param <T> 泛型
     * @return {@link Result}
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setResultCode(CODE.SUCCESS);
        return result;
    }

    /**
     * 成功（返回数据）
     *
     * @param <T> 泛型
     * @return {@link Result}
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setResultCode(CODE.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 失败（自定义返回状态码）
     *
     * @param resultCode 状态码
     * @return {@link Result}
     */
    public static <T> Result<T> fail(Integer resultCode, String message) {
        Result<T> result = new Result<>();
        result.setCode(resultCode);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败（自定义返回状态码）
     *
     * @param resultCode 状态码
     * @return {@link Result}
     */
    public static <T> Result<T> fail(CODE resultCode) {
        Result<T> result = new Result<>();
        result.setResultCode(resultCode);
        return result;
    }

    /**
     * 失败（返回异常状态码）
     *
     * @param e 异常
     * @return {@link Result}
     */
    public static <T> Result<T> fail(UserCenterException e) {
        Result<T> result = new Result<>();
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
        return result;
    }

    /**
     * 失败
     *
     * @param <T> 泛型
     * @return {@link Result}
     */
    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setResultCode(CODE.FAIL);
        return result;
    }

}
