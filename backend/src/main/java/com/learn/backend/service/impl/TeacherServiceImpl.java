package com.learn.backend.service.impl;

import com.learn.backend.dto.LoginResponse;
import com.learn.backend.entity.Teacher;
import com.learn.backend.exception.BusinessException;
import com.learn.backend.mapper.TeacherMapper;
import com.learn.backend.common.ResultCode;
import com.learn.backend.service.TeacherService;
import com.learn.backend.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

/**
 * 教师 Service 实现
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    private static final Logger log = Logger.getLogger(TeacherServiceImpl.class.getName());

    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Override
    public Teacher getByPhone(String phone) {
        return teacherMapper.findByPhone(phone);
    }

    @Override
    public LoginResponse login(String phone, String password) {
        Teacher teacher = teacherMapper.findByPhone(phone);
        if (teacher == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        if (!password.equals(teacher.tpassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR);
        }
        log.info("教师登录成功: " + phone);
        return new LoginResponse(
            teacher.tid(),
            teacher.tname(),
            teacher.tphone(),
            teacher.temail(),
            teacher.tcollege(),
            teacher.timage()
        );
    }

    @Override
    @Transactional
    public boolean register(Teacher teacher) {
        Teacher existing = teacherMapper.findByPhone(teacher.tphone());
        if (existing != null) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXISTS);
        }
        String encryptedPassword = MD5Util.encrypt(teacher.tpassword());
        Teacher newTeacher = new Teacher(
            teacher.tid(),
            teacher.tphone(),
            teacher.temail(),
            encryptedPassword,
            teacher.tname(),
            teacher.tcollege(),
            teacher.timage()
        );
        return teacherMapper.save(newTeacher) > 0;
    }

    @Override
    public int update(Teacher teacher) {
        return teacherMapper.update(teacher);
    }

    @Override
    public String getImage(String phone) {
        Teacher teacher = teacherMapper.findByPhone(phone);
        if (teacher == null) {
            return "error.png";
        }
        return teacher.timage() != null ? teacher.timage() : "error.png";
    }
}
