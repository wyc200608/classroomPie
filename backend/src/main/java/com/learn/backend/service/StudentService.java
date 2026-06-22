package com.learn.backend.service;

import com.learn.backend.entity.Student;

public interface StudentService {
    public Integer update(Student student);
    public Student getInfo(String phone);
    public String register(Student student);
    public String checkLogin(String phone,String password);
    public String getImg(String phone);
}
