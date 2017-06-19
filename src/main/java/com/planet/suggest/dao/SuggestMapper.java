package com.planet.suggest.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.suggest.domain.Suggest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SuggestMapper extends MybatisMapper{
    int deleteByPrimaryKey(Integer sgid);

    int insert(Suggest suggest);

    int insertSelective(Suggest suggest);

    Suggest selectByPrimaryKey(Integer sgid);

    int updateByPrimaryKeySelective(Suggest suggest);

    int updateByPrimaryKey(Suggest suggest);

    //分页查询
    List<Suggest> listPageSelect(Map<String,Object> map);
}