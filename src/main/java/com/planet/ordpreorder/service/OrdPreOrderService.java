package com.planet.ordpreorder.service;

import com.planet.ordpreorder.domain.OrdPreOrder;

import java.util.List;

/**
 * Created by Li on 2016/2/4.
 */
public interface OrdPreOrderService {
    int deleteByPrimaryKey(String poid) throws Exception;

    int insert(OrdPreOrder ordPreOrder) throws Exception;

    int insertSelective(OrdPreOrder ordPreOrder) throws Exception;

    OrdPreOrder selectByPrimaryKey(String poid) throws Exception;

    int updateByPrimaryKeySelective(OrdPreOrder ordPreOrder) throws Exception;

    int updateByPrimaryKey(OrdPreOrder ordPreOrder) throws Exception;

    //根据oid查询预订单详情
    List<OrdPreOrder> selectByOid(String oid) throws Exception;

    //根据Rid查询预订单状态为0的数量
    int selectOrdPreOrderCount(String rid) throws Exception;

    //根据oid查询预订单(有库存)详情
    List<OrdPreOrder> selectSalesPreOrderByOid(String oid) throws Exception;


}
