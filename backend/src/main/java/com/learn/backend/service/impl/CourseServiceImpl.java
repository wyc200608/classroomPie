package com.learn.backend.service.impl;

import com.learn.backend.entity.Course;
import com.learn.backend.mapper.CourseMapper;
import com.learn.backend.mapper.MessageMapper;
import com.learn.backend.mapper.TworkMapper;
import com.learn.backend.service.CourseService;
import com.learn.backend.util.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

/**
 * 课程 Service 实现
 */
@Service
public class CourseServiceImpl implements CourseService {

    private static final Logger log = Logger.getLogger(CourseServiceImpl.class.getName());

    private final CourseMapper courseMapper;
    private final MessageMapper messageMapper;
    private final TworkMapper tworkMapper;

    public CourseServiceImpl(CourseMapper courseMapper, MessageMapper messageMapper, TworkMapper tworkMapper) {
        this.courseMapper = courseMapper;
        this.messageMapper = messageMapper;
        this.tworkMapper = tworkMapper;
    }

    @Override
    public int updateCourse(String cid, String cname, String cdate) {
        return courseMapper.updateCourse(cid, cname, cdate);
    }

    @Override
    public int deleteCourse(String cid) {
        return courseMapper.deleteCourse(cid);
    }

    @Override
    public int archiveCourse(String cid, Integer archive) {
        return courseMapper.archiveCourse(cid, archive);
    }

    @Override
    public List<Map<String, Object>> getCourseByTeacher(String tid) {
        List<Map<String, Object>> teacherList = courseMapper.getCourseByTeacher(tid);
        for (Map<String, Object> item : teacherList) {
            String cid = String.valueOf(item.get("cid"));
            List<Map<String, Object>> lastWork = tworkMapper.getLastWork(cid);
            String work = lastWork.isEmpty() ? "无" : String.valueOf(lastWork.get(0).get("wtitle"));
            item.put("work", work);
        }
        return teacherList;
    }

    @Override
    public List<Map<String, Object>> getCourseByStudent(String sid) {
        List<Map<String, Object>> studentList = courseMapper.getCourseByStudent(sid);
        for (Map<String, Object> item : studentList) {
            String cid = String.valueOf(item.get("cid"));
            List<Map<String, Object>> lastWork = tworkMapper.getLastWork(cid);
            String work = lastWork.isEmpty() ? "无" : String.valueOf(lastWork.get(0).get("wtitle"));
            item.put("work", work);
        }
        return studentList;
    }

    @Override
    public int insert(Course course) {
        return courseMapper.insert(course);
    }

    @Override
    public Course getCourseByInvite(String courseCode) {
        return courseMapper.findByInvite(courseCode);
    }

    @Override
    public List<Map<String, Object>> getCourseMember(String cid) {
        List<Map<String, Object>> memberList = courseMapper.selectCourseTeacher(cid);
        for (Map<String, Object> item : memberList) {
            String job = String.valueOf(item.get("job"));
            item.put("job", "0".equals(job) ? "管理员" : "老师/助教");
            if (item.get("image") == null) {
                item.put("image", "error.png");
            }
        }
        return memberList;
    }

    @Override
    public List<Map<String, Object>> getCourseStudent(String cid) {
        List<Map<String, Object>> studentList = courseMapper.selectCourseSelect(cid);
        for (Map<String, Object> item : studentList) {
            item.putIfAbsent("image", "error.png");
        }
        return studentList;
    }

    @Override
    public List<Map<String, Object>> getCourseGrade(String cid) {
        return courseMapper.selectCourseGrade(cid);
    }

    @Override
    public Course findById(String id) {
        return courseMapper.findById(id);
    }

    @Override
    public int updateNameById(String id, String name) {
        int result = 0;
        Map<String, Object> message = new HashMap<>();
        message.put("mid", new Date().getTime());
        message.put("cid", id);
        message.put("mtitle", "课程更名");
        message.put("mcontent", "课程更名为" + name);
        Calendar cal = Calendar.getInstance();
        String now = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH);
        message.put("mpublish", now);
        message.put("mnum", "-1");
        result += messageMapper.insertMassage(message);

        return result + courseMapper.updateNameById(id, name);
    }

    @Override
    public String generateUniqueInviteCode() {
        String code;
        do {
            code = RandomUtil.generateInviteCode();
        } while (courseMapper.findByInvite(code) != null);
        return code;
    }
}
