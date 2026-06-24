package com.learn.backend.service;

import java.util.List;
import java.util.Map;

/**
 * 消息 Service 接口
 */
public interface MessageService {

    List<Map<String, Object>> getMassageListForStudent(String sphone, String cid);

    List<Map<String, Object>> getMessageListForTeacher(String cid);

    int insert(Map<String, Object> info);

    int insertMainMessage(String mid, String cid, String mtitle, String mcontent, String mpublish, String mnum);
}
