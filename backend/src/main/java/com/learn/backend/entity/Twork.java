package com.learn.backend.entity;

/**
 * 教师作业实体
 */
public record Twork(
    String twid,
    String tid,
    String cid,
    String wtitle,
    String tcontent,
    String tpublish,
    String deadline,
    String scale
) {}
