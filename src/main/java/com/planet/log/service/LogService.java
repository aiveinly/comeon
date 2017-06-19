package com.planet.log.service;

import com.planet.log.domain.Log;

import java.util.Map;

/**
 * Created by Li on 2016/2/23.
 */
public interface LogService {

    int deleteByPrimaryKey(Integer logid) throws Exception;

    int insert(Log log) throws Exception;

    int insertSelective(Log log) throws Exception;

    Log selectByPrimaryKey(Integer logid) throws Exception;

    int updateByPrimaryKeySelective(Log log) throws Exception;

    int updateByPrimaryKey(Log log) throws Exception;

    //日志分页查询
    Log listPageselectByMap(Map<String,Object> map) throws Exception;
}
