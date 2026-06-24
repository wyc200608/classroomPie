package com.learn.backend.mapper;

import com.learn.backend.entity.Twork;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 教师作业 Mapper
 */
@Mapper
public interface TworkMapper {

    int insert(Twork twork);

    int update(Twork twork);

    int delete(String twid);

    List<Map<String, Object>> findWorksByCourseId(String cid);

    List<Map<String, Object>> findWorksForStudent(Map<String, Object> params);

    Map<String, Object> findWorkByTwid(String twid);

    List<Map<String, Object>> getWorkList(String cid);

    List<Map<String, Object>> getLastWork(String cid);
}
