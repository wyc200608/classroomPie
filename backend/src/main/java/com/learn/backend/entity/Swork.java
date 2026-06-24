package com.learn.backend.entity;

/**
 * 学生作业实体
 */
public record Swork(
    String swid,
    String twid,
    String sid,
    String scontent,
    String spublish,
    String correct,
    String score,
    String correction
) {}
