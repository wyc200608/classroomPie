package com.learn.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 消息 Mapper
 */
@Mapper
public interface MessageMapper {

    int insertMassage(Map<String, Object> message);

    int insertSM(Map<String, Object> sm);

    List<Map<String, Object>> getMassageListForStudent(Map<String, Object> params);

    List<Map<String, Object>> getMessageListForTeacher(String cid);
}
