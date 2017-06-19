package com.planet.invoice.service;

import com.planet.invoice.domain.Invoice;

import java.util.List;

/**
 * Created by Li on 2016/2/25.
 */
public interface InvoiceService{
        int deleteByPrimaryKey(Integer iid) throws Exception;

        int insert(Invoice invoice) throws Exception;

        int insertSelective(Invoice invoice) throws Exception;

        Invoice selectByPrimaryKey(Integer iid) throws Exception;

        int updateByPrimaryKeySelective(Invoice invoice) throws Exception;

        int updateByPrimaryKey(Invoice invoice) throws Exception;

        //查询所有发票信息
        List<Invoice> selectAllByUid(Integer uid) throws Exception;
}
