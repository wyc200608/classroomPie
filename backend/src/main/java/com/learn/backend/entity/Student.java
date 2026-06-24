package com.learn.backend.entity;

/**
 * 学生实体
 */
public record Student(
    String sid,
    String sphone,
    String semail,
    String spassword,
    String snum,
    String sname,
    String scollege,
    String simage
) {}
