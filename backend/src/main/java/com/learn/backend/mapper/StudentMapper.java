package com.learn.backend.mapper;

import com.learn.backend.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生 Mapper
 */
@Mapper
public interface StudentMapper {

    int update(Student student);

    int save(Student student);

    Student findByPhone(String phone);
}
