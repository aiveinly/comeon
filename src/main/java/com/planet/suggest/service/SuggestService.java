package com.planet.suggest.service;

import com.planet.suggest.domain.Suggest;

import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/2/24.
 */
public interface SuggestService {
    int deleteByPrimaryKey(Integer sgid) throws Exception;

    int insert(Suggest suggest) throws Exception;

    int insertSelective(Suggest suggest) throws Exception;

    Suggest selectByPrimaryKey(Integer sgid) throws Exception;

    int updateByPrimaryKeySelective(Suggest suggest) throws Exception;

    int updateByPrimaryKey(Suggest suggest) throws Exception;

    //分页查询
    List<Suggest> listPageSelect(Map<String,Object> map) throws Exception;
}
