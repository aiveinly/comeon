package com.planet.ordpreorder.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.ordpreorder.domain.OrdPreOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdPreOrderMapper extends MybatisMapper {
    int deleteByPrimaryKey(String poid);

    int insert(OrdPreOrder ordPreOrder);

    int insertSelective(OrdPreOrder ordPreOrder);

    OrdPreOrder selectByPrimaryKey(String poid);

    int updateByPrimaryKeySelective(OrdPreOrder ordPreOrder);

    int updateByPrimaryKey(OrdPreOrder ordPreOrder);

    //根据oid查询预订单详情
    List<OrdPreOrder> selectByOid(String oid);

    //根据Rid查询预订单状态为0的实体
    List<OrdPreOrder> selectOrdPreOrderCount(String rid);

    //根据oid查询预订单(有库存)详情
    List<OrdPreOrder> selectSalesPreOrderByOid(String oid);

}