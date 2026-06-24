package com.learn.backend.mapper;

import com.learn.backend.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

/**
 * 教师 Mapper
 */
@Mapper
public interface TeacherMapper {

    int update(Teacher teacher);

    int save(Teacher teacher);

    Teacher findByPhone(String phone);
}
