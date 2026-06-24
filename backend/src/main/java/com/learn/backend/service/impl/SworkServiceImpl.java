package com.learn.backend.service.impl;

import com.learn.backend.entity.Swork;
import com.learn.backend.mapper.StudentMapper;
import com.learn.backend.mapper.SworkMapper;
import com.learn.backend.service.SworkService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 学生作业 Service 实现
 */
@Service
public class SworkServiceImpl implements SworkService {

    private static final Logger log = Logger.getLogger(SworkServiceImpl.class.getName());

    private final SworkMapper sworkMapper;
    private final StudentMapper studentMapper;

    public SworkServiceImpl(SworkMapper sworkMapper, StudentMapper studentMapper) {
        this.sworkMapper = sworkMapper;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<Map<String, Object>> getSubmit(String twid) {
        return sworkMapper.getSubmit(twid);
    }

    @Override
    public List<Map<String, Object>> getNoSubmit(String twid) {
        return sworkMapper.getNoSubmit(twid);
    }

    @Override
    public List<Map<String, Object>> getAll(String twid) {
        List<Map<String, Object>> all = new ArrayList<>(sworkMapper.getSubmit(twid));
        all.addAll(sworkMapper.getNoSubmit(twid));
        return all;
    }

    @Override
    public int updateCorrection(String swid, String correction) {
        return sworkMapper.updateCorrection(swid, correction);
    }

    @Override
    public int updateScore(String swid, String score) {
        return sworkMapper.updateScore(swid, score);
    }

    @Override
    public int updateSContent(String swid, String content) {
        return sworkMapper.updateSContent(swid, content);
    }

    @Override
    public int insert(Swork swork) {
        var student = studentMapper.findByPhone(swork.sid());
        String sid = student != null ? student.sid() : swork.sid();
        Swork newSwork = new Swork(
            swork.swid(),
            swork.twid(),
            sid,
            swork.scontent(),
            swork.spublish(),
            swork.correct(),
            swork.score(),
            swork.correction()
        );
        return sworkMapper.insert(newSwork);
    }
}
