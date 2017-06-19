package com.planet.invoice.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.invoice.domain.Invoice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceMapper extends MybatisMapper{
    int deleteByPrimaryKey(Integer iid);

    int insert(Invoice invoice);

    int insertSelective(Invoice invoice);

    Invoice selectByPrimaryKey(Integer iid);

    int updateByPrimaryKeySelective(Invoice invoice);

    int updateByPrimaryKey(Invoice invoice);

    //查询所有发票信息
    List<Invoice> selectAllByUid(Integer uid);
}