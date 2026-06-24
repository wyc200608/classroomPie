package com.learn.backend.entity;

/**
 * 教师实体
 */
public record Teacher(
    String tid,
    String tphone,
    String temail,
    String tpassword,
    String tname,
    String tcollege,
    String timage
) {}
