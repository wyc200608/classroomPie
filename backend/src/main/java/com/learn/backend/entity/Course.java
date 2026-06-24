package com.learn.backend.entity;

/**
 * 课程实体
 */
public record Course(
    String cid,
    String tid,
    String cname,
    String cdate,
    Integer cnum,
    String invite,
    Integer archive
) {}
