package com.learn.backend.entity;

/**
 * 学习课程关联实体 (学生-课程)
 */
public record StudyCourse(
    String scid,
    String sid,
    String cid,
    String grade,
    Integer sarchive,
    Integer sort
) {}
