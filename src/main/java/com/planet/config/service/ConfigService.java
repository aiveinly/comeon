package com.planet.config.service;

import com.planet.config.domain.Config;

/**
 * Created by Li on 2016/2/25.
 */
public interface ConfigService {

    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(Config config) throws Exception;

    int insertSelective(Config config) throws Exception;

    Config selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(Config config) throws Exception;

    int updateByPrimaryKey(Config config) throws Exception;
}
