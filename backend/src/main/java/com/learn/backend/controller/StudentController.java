package com.learn.backend.controller;

import com.learn.backend.common.Result;
import com.learn.backend.dto.LoginRequest;
import com.learn.backend.dto.LoginResponse;
import com.learn.backend.entity.Student;
import com.learn.backend.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

/**
 * 学生 Controller
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {

    private static final Logger log = Logger.getLogger(StudentController.class.getName());

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("学生登录请求: phone=" + request.phone());
        LoginResponse response = studentService.login(request.phone(), request.password());
        return Result.success(response);
    }

    @PostMapping("/getInfo")
    public Result<Student> getInfo(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        Student student = studentService.getByPhone(phone);
        if (student == null) {
            return Result.failed("学生不存在");
        }
        return Result.success(student);
    }

    @PostMapping("/getStudent")
    public Result<Student> getStudent(@RequestBody Map<String, String> params) {
        return getInfo(params);
    }
    @PostMapping("/update")
    public Result<Void> update(@RequestBody Student student) {
        studentService.update(student);
        return Result.success();
    }

    @PostMapping("/updateStudent")
    public Result<Void> updateStudent(@RequestBody Student student) {
        return update(student);
    }
    @PostMapping("/register")
    public Result<Void> register(@RequestBody Student student) {
        studentService.register(student);
        return Result.success();
    }

    @PostMapping("/getHeaderImg")
    public Result<String> getHeaderImg(@RequestBody Map<String, String> params) {
        String image = studentService.getImage(params.get("phone"));
        return Result.success(image);
    }
}
