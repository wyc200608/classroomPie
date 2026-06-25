package com.learn.backend.controller;

import com.learn.backend.common.Result;
import com.learn.backend.dto.LoginRequest;
import com.learn.backend.dto.LoginResponse;
import com.learn.backend.entity.Teacher;
import com.learn.backend.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

/**
 * 教师 Controller
 */
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private static final Logger log = Logger.getLogger(TeacherController.class.getName());

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("教师登录请求: phone=" + request.phone());
        LoginResponse response = teacherService.login(request.phone(), request.password());
        return Result.success(response);
    }

    @PostMapping("/getInfo")
    public Result<Teacher> getInfo(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        Teacher teacher = teacherService.getByPhone(phone);
        if (teacher == null) {
            return Result.failed("教师不存在");
        }
        return Result.success(teacher);
    }

    @PostMapping("/update")
    public Result<Void> update(@RequestBody Teacher teacher) {
        teacherService.update(teacher);
        return Result.success();
    }

    @PostMapping("/register")
    public Result<Void> register(@RequestBody Teacher teacher) {
        teacherService.register(teacher);
        return Result.success();
    }

    @PostMapping("/getHeaderImg")
    public Result<String> getHeaderImg(@RequestBody Map<String, String> params) {
        String image = teacherService.getImage(params.get("phone"));
        return Result.success(image);
    }
}
