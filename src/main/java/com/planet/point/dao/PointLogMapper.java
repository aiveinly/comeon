package com.planet.point.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.point.domain.PointLog;

import java.util.List;
import java.util.Map;

public interface PointLogMapper extends MybatisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PointLog record);

    int insertSelective(PointLog record);

    PointLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PointLog record);

    int updateByPrimaryKey(PointLog record);


    /**
     * 分页查询积分日志
     *
     * @param map
     * @return
     */
    List<PointLog> listPagePointLog(Map map);

}