package com.learn.backend.service.impl;

import com.learn.backend.entity.StudyCourse;
import com.learn.backend.mapper.CourseMapper;
import com.learn.backend.mapper.StudyCourseMapper;
import com.learn.backend.service.StudyCourseService;
import com.learn.backend.util.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 学习课程 Service 实现
 */
@Service
public class StudyCourseServiceImpl implements StudyCourseService {

    private static final Logger log = Logger.getLogger(StudyCourseServiceImpl.class.getName());

    private final StudyCourseMapper studyCourseMapper;
    private final CourseMapper courseMapper;

    public StudyCourseServiceImpl(StudyCourseMapper studyCourseMapper, CourseMapper courseMapper) {
        this.studyCourseMapper = studyCourseMapper;
        this.courseMapper = courseMapper;
    }

    @Override
    public List<Map<String, Object>> getCourseByStudent(String sid) {
        return studyCourseMapper.getCourseByStudent(sid);
    }

    @Override
    public int insert(StudyCourse sc) {
        courseMapper.coursePeopleAdd(sc.cid());
        return studyCourseMapper.insert(sc);
    }

    @Override
    public int sizeBySid(String sid) {
        Map<String, Object> result = studyCourseMapper.sizeBySid(sid);
        if (result == null) {
            return 0;
        }
        Object value = result.get("countCourse");
        if (value == null) {
            return 0;
        }
        return ((Number) value).intValue();
    }

    @Override
    public StudyCourse getInfoByCidAndSid(String cid, String sid) {
        return studyCourseMapper.findByCondition(sid, cid);
    }

    @Override
    public StudyCourse getInfoById(String scId) {
        return studyCourseMapper.findById(scId);
    }

    @Override
    public int archiveCourse(String scId, Integer archive) {
        return studyCourseMapper.archiveCourse(scId, archive);
    }

    @Override
    public int deleteCourse(String scId) {
        StudyCourse sc = studyCourseMapper.findById(scId);
        if (sc != null) {
            courseMapper.coursePeopleDel(sc.cid());
        }
        return studyCourseMapper.delete(scId);
    }

    @Override
    public List<Map<String, Object>> selectArchiveCourse(String sid) {
        return studyCourseMapper.selectArchiveCourse(sid);
    }

    @Override
    public String generateUniqueScid() {
        String scId;
        do {
            scId = RandomUtil.generateNumericString(15);
        } while (studyCourseMapper.findById(scId) != null);
        return scId;
    }
}
