package com.learn.backend.controller;

import com.learn.backend.common.Result;
import com.learn.backend.entity.StudyCourse;
import com.learn.backend.service.CourseService;
import com.learn.backend.service.StudentService;
import com.learn.backend.service.StudyCourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 学习课程 Controller (学生-课程关联)
 */
@RestController
@RequestMapping("/api/sc")
public class StudyController {

    private static final Logger log = Logger.getLogger(StudyController.class.getName());

    private final StudyCourseService studyCourseService;
    private final StudentService studentService;
    private final CourseService courseService;

    public StudyController(StudyCourseService studyCourseService, StudentService studentService, CourseService courseService) {
        this.studyCourseService = studyCourseService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @PostMapping("/findArchiveCourse")
    public Result<List<Map<String, Object>>> findArchiveCourse(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String sid = studentService.getByPhone(phone).sid();
        return Result.success(studyCourseService.selectArchiveCourse(sid));
    }

    @PostMapping("/deleteCourse")
    public Result<Void> deleteCourse(@RequestBody Map<String, String> params) {
        String scid = params.get("scid");
        return studyCourseService.deleteCourse(scid) >= 1 ? Result.success() : Result.failed();
    }

    @PostMapping("/archiveCourse")
    public Result<Void> archiveCourse(@RequestBody Map<String, Object> params) {
        String scid = String.valueOf(params.get("scid"));
        int archive = Integer.parseInt(String.valueOf(params.get("archive")));
        return studyCourseService.archiveCourse(scid, archive) >= 1 ? Result.success() : Result.failed();
    }

    @PostMapping("/getCourse")
    public Result<List<Map<String, Object>>> getCourseByStudent(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String sid = studentService.getByPhone(phone).sid();
        return Result.success(studyCourseService.getCourseByStudent(sid));
    }

    @PostMapping("/addStudyCourse")
    public Result<Void> addStudyCourse(@RequestBody Map<String, String> params) {
        String invite = params.get("invite");
        String sid = params.get("sid");

        var course = courseService.getCourseByInvite(invite);
        if (course == null) {
            return Result.failed("课程不存在");
        }
        if (course.archive() != 0) {
            return Result.failed("课程已归档");
        }
        if (studyCourseService.getInfoByCidAndSid(course.cid(), sid) != null) {
            return Result.failed("已加入该课程");
        }

        StudyCourse sc = new StudyCourse(
            studyCourseService.generateUniqueScid(),
            sid,
            course.cid(),
            "-1",
            0,
            studyCourseService.sizeBySid(sid) + 1
        );

        int result = studyCourseService.insert(sc);
        return result >= 1 ? Result.success() : Result.failed();
    }
}
