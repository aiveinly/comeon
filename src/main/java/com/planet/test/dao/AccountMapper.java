package com.planet.test.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.test.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface AccountMapper extends MybatisMapper {
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(Account record);

    int insertSelective(Account record);

//    Account selectByPrimaryKey(Integer id);
//
//    int updateByPrimaryKeySelective(Account record);
//
//    int updateByPrimaryKey(Account record);

    List<Integer> listPageIds(Map map);
}