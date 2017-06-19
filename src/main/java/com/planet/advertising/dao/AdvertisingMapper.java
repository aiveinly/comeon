package com.planet.advertising.dao;

import com.planet.advertising.domain.Advertising;
import com.planet.common.mybatis.MybatisMapper;

import java.util.List;
import java.util.Map;

public interface AdvertisingMapper extends MybatisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertising record);

    int insertSelective(Advertising record);

    Advertising selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advertising record);

    int updateByPrimaryKey(Advertising record);

    List<Advertising> listPageAdvertising(Map map);

    List<Advertising> selectAdvertisingList(Map map);
}