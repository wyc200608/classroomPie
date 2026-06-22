package com.learn.backend.mapper;


import com.learn.backend.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CourseMapper {
    List<Map> selectCourseGrade(String cid);

    List<Map> selectCourseTeacher(String cid);

    List<Map> selectCourseSelect(String cid);

    Integer coursePeopleDel(String cid);

    Integer coursePeopleAdd(String cid);

    Integer updateCourse(String cid, String cname, String cdate);

    Integer deleteCourse(String cid);

    Integer archiveCourse(String cid, Integer archive);

    Integer insert(Course course);

    Course findByInvite(String courseCode);

    List<Map> getCourseByStudent(String sid);

    List<Map> getCourseByTeacher(String tid);

    Course findById(String id);

    Integer updateNameById(HashMap info);

}
