package com.learn.backend.dto;

/**
 * 登录请求参数
 */
public record LoginRequest(
    String phone,
    String password
) {}
