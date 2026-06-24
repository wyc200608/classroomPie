package com.learn.backend.mapper;

import com.learn.backend.entity.StudyCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 学习课程 Mapper (学生-课程关联)
 */
@Mapper
public interface StudyCourseMapper {

    int insert(StudyCourse sc);

    int delete(String scid);

    int archiveCourse(@Param("scId") String scId, @Param("archive") Integer archive);

    StudyCourse findById(String scid);

    StudyCourse findByCondition(@Param("sid") String sid, @Param("cid") String cid);

    List<Map<String, Object>> getCourseByStudent(String sid);

    List<Map<String, Object>> selectArchiveCourse(String sid);

    Map<String, Object> sizeBySid(String sid);
}
