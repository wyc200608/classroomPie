package com.learn.backend.service;

import com.learn.backend.entity.Swork;

import java.util.List;
import java.util.Map;

/**
 * 学生作业 Service 接口
 */
public interface SworkService {

    List<Map<String, Object>> getSubmit(String twid);

    List<Map<String, Object>> getNoSubmit(String twid);

    List<Map<String, Object>> getAll(String twid);

    int updateCorrection(String swid, String correction);

    int updateScore(String swid, String score);

    int updateSContent(String swid, String content);

    int insert(Swork swork);
}
