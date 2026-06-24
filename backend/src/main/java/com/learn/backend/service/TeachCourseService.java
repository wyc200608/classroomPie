package com.learn.backend.service;

import com.learn.backend.entity.TeachCourse;

import java.util.List;
import java.util.Map;

/**
 * 授课课程 Service 接口 (教师-课程关联)
 */
public interface TeachCourseService {

    int insert(TeachCourse tc);

    TeachCourse getInfoById(String tcId);

    TeachCourse getInfoByCidAndTid(String cid, String tid);

    int sizeByTid(String tid);

    int archiveCourse(String tcid, Integer archive);

    List<Map<String, Object>> selectArchiveCourse(String tid);

    String generateUniqueTcid();
}
