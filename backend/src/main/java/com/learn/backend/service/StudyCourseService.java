package com.learn.backend.service;

import com.learn.backend.entity.StudyCourse;

import java.util.List;
import java.util.Map;

public interface StudyCourseService {
    public List<Map> getCourseByStudent(String sid);

    public Integer insert(StudyCourse sc);

    public Integer sizeBySid(String sid);

    public StudyCourse getInfoByCidAndSid(String cid, String sid);

    public StudyCourse getInfoById(String scId);

    public Integer archiveCourse(String scId, Integer archive);

    public Integer deleteCourse(String scId);

    public List<Map> selectArchiveCourse(String sid);
}
