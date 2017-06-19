package com.planet.config.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.config.domain.Config;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigMapper extends MybatisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Config config);

    int insertSelective(Config config);

    Config selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Config config);

    int updateByPrimaryKey(Config config);
}