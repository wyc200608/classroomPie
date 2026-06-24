package com.learn.backend.mapper;

import com.learn.backend.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 课程 Mapper
 */
@Mapper
public interface CourseMapper {

    int insert(Course course);

    int update(Course course);

    int updateCourse(@Param("cid") String cid, @Param("cname") String cname, @Param("cdate") String cdate);

    int deleteCourse(String cid);

    int archiveCourse(@Param("cid") String cid, @Param("archive") Integer archive);

    int updateNameById(@Param("id") String id, @Param("name") String name);

    int coursePeopleAdd(String cid);

    int coursePeopleDel(String cid);

    Course findById(String id);

    Course findByInvite(String invite);

    List<Map<String, Object>> getCourseByTeacher(String tid);

    List<Map<String, Object>> getCourseByStudent(String sid);

    List<Map<String, Object>> selectCourseTeacher(String cid);

    List<Map<String, Object>> selectCourseSelect(String cid);

    List<Map<String, Object>> selectCourseGrade(String cid);
}
