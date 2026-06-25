package com.learn.backend.controller;

import com.learn.backend.common.Result;
import com.learn.backend.entity.Course;
import com.learn.backend.entity.TeachCourse;
import com.learn.backend.service.CourseService;
import com.learn.backend.service.StudentService;
import com.learn.backend.service.TeachCourseService;
import com.learn.backend.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 课程 Controller
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    private static final Logger log = Logger.getLogger(CourseController.class.getName());

    private final CourseService courseService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final TeachCourseService teachCourseService;

    public CourseController(CourseService courseService, TeacherService teacherService,
                           StudentService studentService, TeachCourseService teachCourseService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.teachCourseService = teachCourseService;
    }

    @PostMapping("/updateCourse")
    public Result<Void> updateCourse(@RequestBody Map<String, Object> info) {
        int result = courseService.updateCourse(
            String.valueOf(info.get("cid")),
            String.valueOf(info.get("cname")),
            String.valueOf(info.get("cdate"))
        );
        return result >= 1 ? Result.success() : Result.failed();
    }

    @PostMapping("/deleteCourse")
    public Result<Void> deleteCourse(@RequestBody Map<String, Object> info) {
        int result = courseService.deleteCourse(String.valueOf(info.get("cid")));
        return result >= 1 ? Result.success() : Result.failed();
    }

    @PostMapping("/archiveCourse")
    public Result<Void> archiveCourse(@RequestBody Map<String, Object> info) {
        int archive = Integer.parseInt(String.valueOf(info.get("archive")));
        if (archive == 0) {
            teachCourseService.archiveCourse(String.valueOf(info.get("tcid")), archive);
            courseService.archiveCourse(String.valueOf(info.get("cid")), archive);
        } else {
            courseService.archiveCourse(String.valueOf(info.get("cid")), archive);
        }
        return Result.success();
    }

    @PostMapping("/createCourse")
    public Result<Map<String, String>> createCourse(@RequestBody Map<String, Object> courseInfo) {
        Course course = new Course(
            String.valueOf(new Date().getTime()),
            String.valueOf(courseInfo.get("tid")),
            String.valueOf(courseInfo.get("cname")),
            String.valueOf(courseInfo.get("cdate")),
            0,
            courseService.generateUniqueInviteCode(),
            0
        );

        int flag = courseService.insert(course);
        if (flag >= 1) {
            TeachCourse tc = new TeachCourse(
                teachCourseService.generateUniqueTcid(),
                String.valueOf(courseInfo.get("tid")),
                course.cid(),
                0,
                0,
                teachCourseService.sizeByTid(String.valueOf(courseInfo.get("tid")))
            );
            teachCourseService.insert(tc);
        }
        log.info("创建课程结果: " + (flag >= 1 ? "success" : "failed") + ", cid: " + course.cid());
        return Result.success(Map.of("msg", flag >= 1 ? "success" : "failed", "cid", course.cid()));
    }

    @PostMapping("/getCourseByTeacher")
    public Result<List<Map<String, Object>>> getTeacherCourse(@RequestBody Map<String, Object> info) {
        String phone = String.valueOf(info.get("phone"));
        var teacher = teacherService.getByPhone(phone);
        return Result.success(courseService.getCourseByTeacher(teacher.tid()));
    }

    @PostMapping("/getCourseByStudent")
    public Result<List<Map<String, Object>>> getStudentCourse(@RequestBody Map<String, Object> info) {
        String phone = String.valueOf(info.get("phone"));
        var student = studentService.getByPhone(phone);
        return Result.success(courseService.getCourseByStudent(student.sid()));
    }

    @PostMapping("/getCourseMember")
    public Result<List<Map<String, Object>>> getCourseMember(@RequestBody Map<String, Object> info) {
        return Result.success(courseService.getCourseMember(String.valueOf(info.get("cid"))));
    }

    @PostMapping("/getCourseStudent")
    public Result<List<Map<String, Object>>> getCourseStudent(@RequestBody Map<String, Object> info) {
        return Result.success(courseService.getCourseStudent(String.valueOf(info.get("cid"))));
    }

    @PostMapping("/getStudents")
    public Result<List<Map<String, Object>>> getStudents(@RequestBody Map<String, Object> info) {
        return getCourseStudent(info);
    }
    @PostMapping("/getCourseGrade")
    public Result<List<Map<String, Object>>> getCourseGrade(@RequestBody Map<String, Object> info) {
        return Result.success(courseService.getCourseGrade(String.valueOf(info.get("cid"))));
    }

    @PostMapping("/getOneCourse")
    public Result<Course> getOneCourse(@RequestBody Map<String, Object> info) {
        String id = String.valueOf(info.get("id"));
        Course course = courseService.findById(id);
        return course != null ? Result.success(course) : Result.failed("课程不存在");
    }

    @PostMapping("/getCourse")
    public Result<Course> getCourse(@RequestBody Map<String, Object> info) {
        return getOneCourse(info);
    }
    @PostMapping("/updateNameById")
    public Result<Void> updateNameById(@RequestBody Map<String, Object> info) {
        int result = courseService.updateNameById(String.valueOf(info.get("id")), String.valueOf(info.get("name")));
        return result >= 1 ? Result.success() : Result.failed();
    }
}
