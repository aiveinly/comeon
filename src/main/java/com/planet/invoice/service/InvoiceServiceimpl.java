package com.planet.invoice.service;

import com.planet.invoice.dao.InvoiceMapper;
import com.planet.invoice.domain.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Li on 2016/2/25.
 */
@Service
@Transactional
public class InvoiceServiceimpl implements InvoiceService{

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceimpl.class);

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer iid) throws Exception {
        int i = 0;
        i = invoiceMapper.deleteByPrimaryKey(iid);
        if(i==0){
            logger.info("删除失败");
        }else if(i==1){
            logger.info("删除成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(Invoice invoice) throws Exception {
        int i = 0;
        i = invoiceMapper.insert(invoice);
        if(i==0){
            logger.info("插入失败");
        }else if(i==1){
            logger.info("插入成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(Invoice invoice) throws Exception {
        int i = 0;
        i = invoiceMapper.insertSelective(invoice);
        if(i==0){
            logger.info("插入发票失败");
        }else if(i==1){
            logger.info("插入发票成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Invoice selectByPrimaryKey(Integer iid) throws Exception {
        return  invoiceMapper.selectByPrimaryKey(iid);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(Invoice invoice) throws Exception {
        int i = 0;
        i = invoiceMapper.updateByPrimaryKeySelective(invoice);
        if(i==0){
            logger.info("更新发票失败");
        }else if(i==1){
            logger.info("更新发票成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(Invoice invoice) throws Exception {
        int i = 0;
        i = invoiceMapper.updateByPrimaryKeySelective(invoice);
        if(i==0){
            logger.info("更新发票失败");
        }else if(i==1){
            logger.info("更新发票成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Invoice> selectAllByUid(Integer uid) throws Exception {
        return invoiceMapper.selectAllByUid(uid);
    }
}
