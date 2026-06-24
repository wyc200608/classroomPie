package com.learn.backend.entity;

/**
 * 授课课程关联实体 (教师-课程)
 */
public record TeachCourse(
    String tcid,
    String tid,
    String cid,
    Integer tarchive,
    Integer job,
    Integer tsort
) {}
