package com.planet.ordorder.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.ordorder.domain.OrdOrder;
import com.planet.vo.OrdOrderVo;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrdOrderMapper extends MybatisMapper {
    int deleteByPrimaryKey(String oid);

    int insert(OrdOrder ordOrder);

    int insertSelective(OrdOrder ordOrder);

    OrdOrder selectByPrimaryKey(String oid);

    int updateByPrimaryKeySelective(OrdOrder ordOrder);

    int updateByPrimaryKey(OrdOrder ordOrder);

    //查询全部订单
    List<OrdOrderVo> listPageSelectOrder(Map map);

    List<OrdOrderVo> selectAllOrder(Map map);
}