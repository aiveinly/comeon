package com.planet.idrule.service;

import com.planet.idrule.domain.IdRule;

import java.util.List;

/**
 * Created by Li on 2016/1/31.
 */
public interface IdRuleService {
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(IdRule idRule) throws Exception;

    int insertSelective(IdRule idRule) throws Exception;

    IdRule selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(IdRule idRule) throws Exception;

    int updateByPrimaryKey(IdRule idRule) throws Exception;

    //根据numName获取编号
    String selectByNumName(String numName) throws Exception;
}
