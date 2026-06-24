package com.learn.backend.service;

import com.learn.backend.dto.LoginResponse;
import com.learn.backend.entity.Teacher;

/**
 * 教师 Service 接口
 */
public interface TeacherService {

    /**
     * 根据手机号查询教师
     */
    Teacher getByPhone(String phone);

    /**
     * 教师登录验证
     */
    LoginResponse login(String phone, String password);

    /**
     * 教师注册
     */
    boolean register(Teacher teacher);

    /**
     * 更新教师信息
     */
    int update(Teacher teacher);

    /**
     * 获取教师头像文件名
     */
    String getImage(String phone);
}
