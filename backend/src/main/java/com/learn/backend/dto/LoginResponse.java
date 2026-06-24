package com.learn.backend.dto;

/**
 * 登录响应数据
 */
public record LoginResponse(
    String id,
    String name,
    String phone,
    String email,
    String college,
    String image
) {}
