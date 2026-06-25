package com.learn.backend.controller;

import com.learn.backend.common.Result;
import com.learn.backend.entity.TeachCourse;
import com.learn.backend.service.CourseService;
import com.learn.backend.service.TeachCourseService;
import com.learn.backend.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 授课课程 Controller (教师-课程关联)
 */
@RestController
@RequestMapping("/api/tc")
public class TeachCourseController {

    private static final Logger log = Logger.getLogger(TeachCourseController.class.getName());

    private final TeachCourseService teachCourseService;
    private final CourseService courseService;
    private final TeacherService teacherService;

    public TeachCourseController(TeachCourseService teachCourseService, CourseService courseService, TeacherService teacherService) {
        this.teachCourseService = teachCourseService;
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @PostMapping("/findArchiveCourse")
    public Result<List<Map<String, Object>>> findArchiveCourse(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String tid = teacherService.getByPhone(phone).tid();
        return Result.success(teachCourseService.selectArchiveCourse(tid));
    }

    @PostMapping("/archiveCourse")
    public Result<Void> archiveCourse(@RequestBody Map<String, Object> params) {
        String tcid = String.valueOf(params.get("tcid"));
        int archive = Integer.parseInt(String.valueOf(params.get("archive")));
        int result = teachCourseService.archiveCourse(tcid, archive);
        return result >= 1 ? Result.success() : Result.failed();
    }

    @PostMapping("/addTeachCourse")
    public Result<Void> addTeachCourse(@RequestBody Map<String, String> params) {
        String invite = params.get("invite");
        String tid = params.get("tid");

        var course = courseService.getCourseByInvite(invite);
        if (course == null) {
            return Result.failed("课程不存在");
        }
        if (course.archive() != 0) {
            return Result.failed("课程已归档");
        }
        if (teachCourseService.getInfoByCidAndTid(course.cid(), tid) != null) {
            return Result.failed("已加入该课程");
        }

        TeachCourse tc = new TeachCourse(
            teachCourseService.generateUniqueTcid(),
            tid,
            course.cid(),
            0,
            1,
            teachCourseService.sizeByTid(tid) + 1
        );

        int result = teachCourseService.insert(tc);
        return result >= 1 ? Result.success() : Result.failed();
    }

    @PostMapping("/addTeachCourseByCreator")
    public Result<Void> addTeachCourseByCreator(@RequestBody Map<String, String> params) {
        String cid = params.get("cid");
        String tid = params.get("tid");

        TeachCourse tc = new TeachCourse(
            teachCourseService.generateUniqueTcid(),
            tid,
            cid,
            0,
            0,
            teachCourseService.sizeByTid(tid)
        );

        int result = teachCourseService.insert(tc);
        return result >= 1 ? Result.success() : Result.failed();
    }
}
