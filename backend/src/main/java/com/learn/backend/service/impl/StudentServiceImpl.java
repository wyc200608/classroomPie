package com.learn.backend.service.impl;

import com.learn.backend.dto.LoginResponse;
import com.learn.backend.entity.Student;
import com.learn.backend.exception.BusinessException;
import com.learn.backend.mapper.StudentMapper;
import com.learn.backend.common.ResultCode;
import com.learn.backend.service.StudentService;
import com.learn.backend.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

/**
 * 学生 Service 实现
 */
@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger log = Logger.getLogger(StudentServiceImpl.class.getName());

    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public Student getByPhone(String phone) {
        return studentMapper.findByPhone(phone);
    }

    @Override
    public LoginResponse login(String phone, String password) {
        Student student = studentMapper.findByPhone(phone);
        if (student == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        String encryptedPassword = MD5Util.encrypt(password);
        if (!encryptedPassword.equals(student.spassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR);
        }
        log.info("学生登录成功: " + phone);
        return new LoginResponse(
            student.sid(),
            student.sname(),
            student.sphone(),
            student.semail(),
            student.scollege(),
            student.simage()
        );
    }

    @Override
    @Transactional
    public boolean register(Student student) {
        Student existing = studentMapper.findByPhone(student.sphone());
        if (existing != null) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXISTS);
        }
        String encryptedPassword = MD5Util.encrypt(student.spassword());
        Student newStudent = new Student(
            student.sid(),
            student.sphone(),
            student.semail(),
            encryptedPassword,
            student.snum(),
            student.sname(),
            student.scollege(),
            student.simage()
        );
        return studentMapper.save(newStudent) > 0;
    }

    @Override
    public int update(Student student) {
        return studentMapper.update(student);
    }

    @Override
    public String getImage(String phone) {
        Student student = studentMapper.findByPhone(phone);
        if (student == null) {
            return "error.png";
        }
        return student.simage() != null ? student.simage() : "error.png";
    }
}
