package com.planet.idrule.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.idrule.domain.IdRule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IdRuleMapper extends MybatisMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(IdRule idRule);

    int insertSelective(IdRule idRule);

    IdRule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IdRule idRule);

    int updateByPrimaryKey(IdRule idRule);

    //根据numName获取编号
    IdRule selectByNumName(@Param(value="numName")String numName);
}