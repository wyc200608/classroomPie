package com.learn.backend.service;

import com.learn.backend.dto.LoginResponse;
import com.learn.backend.entity.Student;

/**
 * 学生 Service 接口
 */
public interface StudentService {

    /**
     * 根据手机号查询学生
     */
    Student getByPhone(String phone);

    /**
     * 学生登录验证
     */
    LoginResponse login(String phone, String password);

    /**
     * 学生注册
     */
    boolean register(Student student);

    /**
     * 更新学生信息
     */
    int update(Student student);

    /**
     * 获取学生头像文件名
     */
    String getImage(String phone);
}
