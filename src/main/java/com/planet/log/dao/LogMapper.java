package com.planet.log.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.log.domain.Log;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface LogMapper extends MybatisMapper{
    int deleteByPrimaryKey(Integer logid);

    int insert(Log log);

    int insertSelective(Log log);

    Log selectByPrimaryKey(Integer logid);

    int updateByPrimaryKeySelective(Log log);

    int updateByPrimaryKey(Log log);

    //日志分页查询
    Log listPageselectByMap(Map<String,Object> map);
}