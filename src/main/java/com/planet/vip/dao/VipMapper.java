package com.planet.vip.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.vip.domain.Vip;

import java.util.List;
import java.util.Map;

public interface VipMapper extends MybatisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Vip record);

    int insertSelective(Vip record);

    Vip selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Vip record);

    int updateByPrimaryKey(Vip record);

    List<Vip> listPageSelect(Map<String, Object> map);
}