package com.learn.backend.common;

/**
 * 统一响应结果码
 */
public enum ResultCode {
    SUCCESS(true, 20000, "操作成功"),
    FAILED(false, 20001, "操作失败"),
    VALIDATE_FAILED(false, 20002, "参数校验失败"),
    UNAUTHORIZED(false, 20003, "未登录或登录已过期"),
    FORBIDDEN(false, 20004, "没有相关权限"),
    NOT_FOUND(false, 20005, "资源不存在"),
    INTERNAL_SERVER_ERROR(false, 20006, "服务器内部错误"),

    // 业务相关
    USER_NOT_FOUND(false, 30001, "用户不存在"),
    PASSWORD_ERROR(false, 30002, "密码错误"),
    USER_ALREADY_EXISTS(false, 30003, "用户已存在"),
    COURSE_NOT_FOUND(false, 30004, "课程不存在"),
    COURSE_ARCHIVED(false, 30005, "课程已归档"),
    COURSE_ALREADY_JOINED(false, 30006, "已加入该课程");

    private final boolean success;
    private final int code;
    private final String message;

    ResultCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
