package com.learn.backend.entity;

/**
 * 消息实体
 */
public record Message(
    String mid,
    String cid,
    String mtitle,
    String mcontent,
    String mpublish,
    String mnum
) {}
