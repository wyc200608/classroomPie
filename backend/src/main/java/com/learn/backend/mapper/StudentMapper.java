package com.learn.backend.mapper;

import com.learn.backend.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 王游
 * @date 2020/6/2
 */
@Mapper
@Repository
public interface StudentMapper {

    /**
     * @author 王游
     * @date 2020/6/5
     * @param student
     * @return
     */
    Integer update(Student student);

    /**
     * @author 王游
     * @date 2020/6/1
     * @action 保存
     * @param student
     */
    void save(Student student);

    /**
     * @author 王游
     * @date 2020/6/1
     * @action 通过号码查询
     * @param phone
     * @return
     */
    Student findByPhone(String phone);
}
