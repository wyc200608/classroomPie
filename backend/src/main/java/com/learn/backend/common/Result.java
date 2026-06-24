package com.learn.backend.common;

/**
 * 统一响应结果封装
 */
public record Result<T>(
    boolean ok,
    int code,
    String message,
    T data
) {
    public static <T> Result<T> success() {
        return new Result<>(true, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(true, ResultCode.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> failed() {
        return new Result<>(false, ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
    }

    public static <T> Result<T> failed(String message) {
        return new Result<>(false, ResultCode.FAILED.getCode(), message, null);
    }

    public static <T> Result<T> failed(ResultCode resultCode) {
        return new Result<>(false, resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static <T> Result<T> failed(int code, String message) {
        return new Result<>(false, code, message, null);
    }

    public static <T> Result<T> validateFailed(String message) {
        return new Result<>(false, ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }
}
