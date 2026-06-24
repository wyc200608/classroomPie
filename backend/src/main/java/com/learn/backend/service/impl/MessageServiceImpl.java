package com.learn.backend.service.impl;

import com.learn.backend.mapper.MessageMapper;
import com.learn.backend.mapper.TworkMapper;
import com.learn.backend.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

/**
 * 消息 Service 实现
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger log = Logger.getLogger(MessageServiceImpl.class.getName());

    private final MessageMapper messageMapper;
    private final TworkMapper tworkMapper;

    public MessageServiceImpl(MessageMapper messageMapper, TworkMapper tworkMapper) {
        this.messageMapper = messageMapper;
        this.tworkMapper = tworkMapper;
    }

    @Override
    public List<Map<String, Object>> getMassageListForStudent(String sphone, String cid) {
        Map<String, Object> params = new HashMap<>();
        params.put("sphone", sphone);
        params.put("cid", cid);
        return messageMapper.getMassageListForStudent(params);
    }

    @Override
    public List<Map<String, Object>> getMessageListForTeacher(String cid) {
        return messageMapper.getMessageListForTeacher(cid);
    }

    @Override
    public int insert(Map<String, Object> info) {
        int result = 0;
        String mid = String.valueOf(new Date().getTime());
        Map<String, Object> twork = tworkMapper.findWorkByTwid(String.valueOf(info.get("twid")));
        String tworkTitle = twork != null ? String.valueOf(twork.get("wtitle")) : "作业";

        Map<String, Object> message = new HashMap<>();
        message.put("mid", mid);
        message.put("cid", info.get("cid"));
        message.put("mtitle", tworkTitle + "催交通知");
        message.put("mcontent", "你该提交" + tworkTitle + "的作业了，请及时提交");
        message.put("mpublish", info.get("mpublish"));
        message.put("mnum", "1");
        result += messageMapper.insertMassage(message);

        Map<String, Object> sm = new HashMap<>();
        sm.put("rid", String.valueOf(new Date().getTime()));
        sm.put("sid", info.get("sid"));
        sm.put("mid", mid);
        sm.put("isread", "0");
        result += messageMapper.insertSM(sm);
        return result;
    }

    @Override
    public int insertMainMessage(String mid, String cid, String mtitle, String mcontent, String mpublish, String mnum) {
        Map<String, Object> message = new HashMap<>();
        message.put("mid", mid);
        message.put("cid", cid);
        message.put("mtitle", mtitle);
        message.put("mcontent", mcontent);
        message.put("mpublish", mpublish);
        message.put("mnum", mnum);
        return messageMapper.insertMassage(message);
    }
}
