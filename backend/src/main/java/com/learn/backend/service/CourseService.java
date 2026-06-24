package com.learn.backend.service;

import com.learn.backend.entity.Course;

import java.util.List;
import java.util.Map;

/**
 * 课程 Service 接口
 */
public interface CourseService {

    int updateCourse(String cid, String cname, String cdate);

    int deleteCourse(String cid);

    int archiveCourse(String cid, Integer archive);

    List<Map<String, Object>> getCourseByTeacher(String tid);

    List<Map<String, Object>> getCourseByStudent(String sid);

    int insert(Course course);

    Course getCourseByInvite(String courseCode);

    List<Map<String, Object>> getCourseMember(String cid);

    List<Map<String, Object>> getCourseStudent(String cid);

    List<Map<String, Object>> getCourseGrade(String cid);

    Course findById(String id);

    int updateNameById(String id, String name);

    String generateUniqueInviteCode();
}
