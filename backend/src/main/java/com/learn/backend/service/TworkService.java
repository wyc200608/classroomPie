package com.learn.backend.service;

import com.learn.backend.entity.Twork;

import java.util.List;
import java.util.Map;

/**
 * 教师作业 Service 接口
 */
public interface TworkService {

    List<Map<String, Object>> findTworkByCourseIdForTeacher(String cid);

    List<Map<String, Object>> findTworkByCourseIdForStudent(String cid, String sid);

    Map<String, Object> findTworkByTwid(String twid);

    int update(Twork twork);

    int delete(String twid);

    int insert(Twork twork);

    List<Map<String, Object>> getNameList(String cid);
}
