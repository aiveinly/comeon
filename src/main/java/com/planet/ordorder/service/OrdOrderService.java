package com.planet.ordorder.service;

import com.planet.ordorder.domain.OrdOrder;
import com.planet.vo.OrdOrderDetailVo;
import com.planet.vo.OrdOrderVo;

import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/2/14.
 */
public interface OrdOrderService {
    int deleteByPrimaryKey(String oid) throws Exception;

    int insert(OrdOrder ordOrder) throws Exception;

    int insertSelective(OrdOrder ordOrder) throws Exception;

    OrdOrder selectByPrimaryKey(String oid) throws Exception;

    int updateByPrimaryKeySelective(OrdOrder ordOrder) throws Exception;

    int updateByPrimaryKey(OrdOrder ordOrder) throws Exception;

    //查询全部订单
    List<OrdOrderVo> listPageSelectOrder(Map map) throws Exception;

    //查询订单详情
    OrdOrderDetailVo selectByOid(String oid) throws Exception;

    //订单完成确认
    int firmOrder(String oid) throws Exception;

    List<Map> selectAllOrderToVo(Map<String, Object> map) throws  Exception;
}
