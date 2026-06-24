package com.learn.backend.mapper;

import com.learn.backend.entity.TeachCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 授课课程 Mapper (教师-课程关联)
 */
@Mapper
public interface TeachCourseMapper {

    int insert(TeachCourse tc);

    int updateArchive(@Param("tcid") String tcid, @Param("archive") Integer archive);

    TeachCourse findById(String tcid);

    TeachCourse findByCondition(@Param("cid") String cid, @Param("tid") String tid);

    List<Map<String, Object>> selectArchiveCourse(String tid);

    Map<String, Object> sizeByTid(String tid);
}
