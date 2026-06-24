package com.learn.backend.service;

import com.learn.backend.entity.StudyCourse;

import java.util.List;
import java.util.Map;

/**
 * 学习课程 Service 接口 (学生-课程关联)
 */
public interface StudyCourseService {

    List<Map<String, Object>> getCourseByStudent(String sid);

    int insert(StudyCourse sc);

    int sizeBySid(String sid);

    StudyCourse getInfoByCidAndSid(String cid, String sid);

    StudyCourse getInfoById(String scId);

    int archiveCourse(String scId, Integer archive);

    int deleteCourse(String scId);

    List<Map<String, Object>> selectArchiveCourse(String sid);

    String generateUniqueScid();
}
