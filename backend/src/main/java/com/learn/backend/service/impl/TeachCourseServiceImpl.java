package com.learn.backend.service.impl;

import com.learn.backend.entity.TeachCourse;
import com.learn.backend.mapper.CourseMapper;
import com.learn.backend.mapper.TeachCourseMapper;
import com.learn.backend.service.TeachCourseService;
import com.learn.backend.util.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 授课课程 Service 实现
 */
@Service
public class TeachCourseServiceImpl implements TeachCourseService {

    private static final Logger log = Logger.getLogger(TeachCourseServiceImpl.class.getName());

    private final TeachCourseMapper teachCourseMapper;
    private final CourseMapper courseMapper;

    public TeachCourseServiceImpl(TeachCourseMapper teachCourseMapper, CourseMapper courseMapper) {
        this.teachCourseMapper = teachCourseMapper;
        this.courseMapper = courseMapper;
    }

    @Override
    public int insert(TeachCourse tc) {
        courseMapper.coursePeopleAdd(tc.cid());
        return teachCourseMapper.insert(tc);
    }

    @Override
    public TeachCourse getInfoById(String tcId) {
        return teachCourseMapper.findById(tcId);
    }

    @Override
    public TeachCourse getInfoByCidAndTid(String cid, String tid) {
        return teachCourseMapper.findByCondition(cid, tid);
    }

    @Override
    public int sizeByTid(String tid) {
        Map<String, Object> result = teachCourseMapper.sizeByTid(tid);
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
    public int archiveCourse(String tcid, Integer archive) {
        return teachCourseMapper.updateArchive(tcid, archive);
    }

    @Override
    public List<Map<String, Object>> selectArchiveCourse(String tid) {
        return teachCourseMapper.selectArchiveCourse(tid);
    }

    @Override
    public String generateUniqueTcid() {
        String tcId;
        do {
            tcId = RandomUtil.generateNumericString(15);
        } while (teachCourseMapper.findById(tcId) != null);
        return tcId;
    }
}
