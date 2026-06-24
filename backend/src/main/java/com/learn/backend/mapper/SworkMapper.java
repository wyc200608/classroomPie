package com.learn.backend.mapper;

import com.learn.backend.entity.Swork;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 学生作业 Mapper
 */
@Mapper
public interface SworkMapper {

    int insert(Swork swork);

    int updateCorrection(@Param("swid") String swid, @Param("correction") String correction);

    int updateScore(@Param("swid") String swid, @Param("score") String score);

    int updateSContent(@Param("swid") String swid, @Param("scontent") String scontent);

    int deleteByTwid(String twid);

    int getNeedCorrect(String twid);

    int getCorrected(String twid);

    int getLock(String twid);

    List<Map<String, Object>> getSubmit(String twid);

    List<Map<String, Object>> getNoSubmit(String twid);
}
