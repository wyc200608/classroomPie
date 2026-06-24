package com.learn.backend.controller;

import com.learn.backend.entity.Course;
import com.learn.backend.entity.Student;
import com.learn.backend.entity.TeachCourse;
import com.learn.backend.entity.Teacher;
import com.learn.backend.service.CourseService;
import com.learn.backend.service.StudentService;
import com.learn.backend.service.TeachCourseService;
import com.learn.backend.service.TeacherService;
import com.learn.backend.util.CourseCode;
import com.learn.backend.util.DefaultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
@CrossOrigin    //允许跨域
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeachCourseService teachCourseService;

    @PostMapping("/updateCourse")
    @ResponseBody
    public String updateCourse(@RequestBody Map<String, Object> info, HttpServletRequest request) {
        return courseService.updateCourse(String.valueOf(info.get("cid")), String.valueOf(info.get("cname")), String.valueOf(info.get("cdate"))) >= 1 ? "success" : "failed";
    }

    @PostMapping("/deleteCourse")
    @ResponseBody
    public String deleteCourse(@RequestBody Map<String, Object> info, HttpServletRequest request) {
        return courseService.deleteCourse(String.valueOf(info.get("cid"))) >= 1 ? "success" : "failed";
    }

    @PostMapping("/archiveCourse")
    @ResponseBody
    public String archiveCourse(@RequestBody Map<String, Object> info, HttpServletRequest request) {
        if (Integer.parseInt(info.get("archive").toString()) == 0) {
            Integer item_1 = teachCourseService.archiveCourse(String.valueOf(info.get("tcid")), Integer.parseInt(info.get("archive").toString()));
            Integer item_2 = courseService.archiveCourse(String.valueOf(info.get("cid")), Integer.parseInt(info.get("archive").toString()));
            return item_1 + item_2 >= 1 ? "success" : "failed";
        }
        return courseService.archiveCourse(String.valueOf(info.get("cid")), Integer.parseInt(info.get("archive").toString())) >= 1 ? "success" : "failed";
    }

    @PostMapping("/createCourse")
    @ResponseBody
    public Map<String, String> createCourse(@RequestBody Map<String, Object> courseInfo, HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();

        Course course = new Course();
        course.setCdate(String.valueOf(courseInfo.get("cdate")));
        course.setCname(String.valueOf(courseInfo.get("cname")));
        course.setTid(String.valueOf(courseInfo.get("tid")));
        String cid = new Date().getTime() + "";
        course.setCid(cid);
        while (true) {
            String courseCode = CourseCode.getCode(8);
            if (courseService.getCourseByInvite(courseCode) == null) {
                course.setInvite(courseCode);
                break;
            }
        }
        course.setCnum(0);
        course.setArchive(0);

        Integer flag = courseService.insert(course);
        if (flag >= 1) {

            TeachCourse tc = new TeachCourse();
            tc.setCid(cid);
            tc.setTarchive(0);
            tc.setJob(0);
            tc.setTsort(teachCourseService.sizeByTid(String.valueOf(courseInfo.get("tid"))));
            tc.setTid(String.valueOf(courseInfo.get("tid")));
            while (true) {
                String tcId = CourseCode.getRandomNum(15);
                if (teachCourseService.getInfoById(tcId) == null) {
                    tc.setTcid(tcId);
                    break;
                }
            }
            teachCourseService.insert(tc);
        }
        result.put("msg", flag >= 1 ? "success" : "failed");
        result.put("cid", cid);

        return result;
    }


    @PostMapping("/getCourseByTeacher")
    @ResponseBody
    public List<Map> getTeacherCourse(@RequestBody Map<String, Object> info) throws Exception {
        Teacher teacher = teacherService.getInfo(String.valueOf(info.get("phone")));
        return courseService.getCourseByTeacher(teacher.getTid());
    }


    @PostMapping("/getCourseByStudent")
    @ResponseBody
    public List<Map> getStudentCourse(@RequestBody Map<String, Object> info) throws Exception {
        Student student = studentService.getInfo(String.valueOf(info.get("phone")));
        return courseService.getCourseByStudent(student.getSid());
    }

    @PostMapping("getCourseMember")
    @ResponseBody
    public List<Map> getCourseMember(@RequestBody Map<String, Object> info) throws IOException {
        List<Map> memberList = courseService.getCourseMember(String.valueOf(info.get("cid")));
        return memberList;
    }

    @PostMapping("getCourseStudent")
    @ResponseBody
    public List<Map> getCourseStudent(@RequestBody Map<String, Object> info) throws IOException {
        List<Map> studentList = courseService.getCourseStudent(String.valueOf(info.get("cid")));
        return studentList;
    }

    @PostMapping("getCourseGrade")
    @ResponseBody
    public List<Map> getCourseGrade(@RequestBody Map<String, Object> info) throws IOException {
        System.out.println(info.get("cid"));
        List<Map> gradeList = courseService.getCourseGrade(String.valueOf(info.get("cid")));
        return gradeList;
    }


    //    王游
    @PostMapping("/getOneCourse")
    @ResponseBody
    public Map<String, Object> getOneCourse(@RequestBody Map<String, Object> info) throws Exception {
        Map<String, Object> result = null;
        String id = info.get("id").toString();
        courseService.findById(info.get("id").toString());
        result = DefaultInfo.convertToMap(courseService.findById(info.get("id").toString()));
        return result;
    }

    @PostMapping("updateNameById")
    @ResponseBody
    public Map<String, Object> updateNameById(@RequestBody Map<String, Object> info) {
        Map<String, Object> result = new HashMap<>();
        Integer integer = courseService.updateNameById(String.valueOf(info.get("id")), String.valueOf(info.get("name")));
        result.put("result", integer);
        return result;
    }

}