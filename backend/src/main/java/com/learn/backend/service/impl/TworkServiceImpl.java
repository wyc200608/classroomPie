package com.learn.backend.service.impl;

import com.learn.backend.entity.Twork;
import com.learn.backend.mapper.MessageMapper;
import com.learn.backend.mapper.SworkMapper;
import com.learn.backend.mapper.TworkMapper;
import com.learn.backend.service.TworkService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

/**
 * 教师作业 Service 实现
 */
@Service
public class TworkServiceImpl implements TworkService {

    private static final Logger log = Logger.getLogger(TworkServiceImpl.class.getName());

    private final TworkMapper tworkMapper;
    private final SworkMapper sworkMapper;
    private final MessageMapper messageMapper;

    public TworkServiceImpl(TworkMapper tworkMapper, SworkMapper sworkMapper, MessageMapper messageMapper) {
        this.tworkMapper = tworkMapper;
        this.sworkMapper = sworkMapper;
        this.messageMapper = messageMapper;
    }

    @Override
    public List<Map<String, Object>> findTworkByCourseIdForTeacher(String cid) {
        List<Map<String, Object>> result = tworkMapper.findWorksByCourseId(cid);
        for (Map<String, Object> item : result) {
            String twid = String.valueOf(item.get("twid"));
            item.put("needCorrect", sworkMapper.getNeedCorrect(twid));
            item.put("corrected", sworkMapper.getCorrected(twid));
            item.put("lock", sworkMapper.getLock(twid));
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> findTworkByCourseIdForStudent(String cid, String sid) {
        Map<String, Object> params = new HashMap<>();
        params.put("cid", cid);
        params.put("sid", sid);
        List<Map<String, Object>> result = tworkMapper.findWorksForStudent(params);
        for (Map<String, Object> item : result) {
            if (item.get("scontent") == null) {
                item.put("scontent", "");
                item.put("correction", "");
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> findTworkByTwid(String twid) {
        Map<String, Object> result = tworkMapper.findWorkByTwid(twid);
        if (result != null) {
            result.put("needCorrect", sworkMapper.getNeedCorrect(twid));
            result.put("corrected", sworkMapper.getCorrected(twid));
            result.put("lock", sworkMapper.getLock(twid));
        }
        return result;
    }

    @Override
    public int update(Twork twork) {
        int result = 0;
        Map<String, Object> message = createMessage(twork.cid(), "作业修改",
            "作业名：" + twork.wtitle() + ";截止时间：" + twork.deadline());
        result += messageMapper.insertMassage(message);
        return result + tworkMapper.update(twork);
    }

    @Override
    public int delete(String twid) {
        int result = 0;
        Map<String, Object> twork = tworkMapper.findWorkByTwid(twid);
        if (twork != null) {
            Map<String, Object> message = createMessage(
                String.valueOf(twork.get("cid")), "作业取消",
                "作业名：" + twork.get("wtitle") + ";截止时间：" + twork.get("deadline"));
            result += messageMapper.insertMassage(message);
        }
        result += sworkMapper.deleteByTwid(twid);
        result += tworkMapper.delete(twid);
        return result;
    }

    @Override
    public int insert(Twork twork) {
        int result = 0;
        Map<String, Object> message = createMessage(twork.cid(), "新增作业",
            "作业名：" + twork.wtitle() + ";截止时间：" + twork.deadline());
        result += messageMapper.insertMassage(message);
        result += tworkMapper.insert(twork);
        return result;
    }

    @Override
    public List<Map<String, Object>> getNameList(String cid) {
        return tworkMapper.getWorkList(cid);
    }

    private Map<String, Object> createMessage(String cid, String title, String content) {
        Map<String, Object> message = new HashMap<>();
        message.put("mid", new Date().getTime());
        message.put("cid", cid);
        message.put("mtitle", title);
        message.put("mcontent", content);
        Calendar cal = Calendar.getInstance();
        String now = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH);
        message.put("mpublish", now);
        message.put("mnum", "-1");
        return message;
    }
}
