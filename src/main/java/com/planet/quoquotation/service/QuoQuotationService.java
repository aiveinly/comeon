package com.planet.quoquotation.service;

import com.planet.quoquotation.domain.QuoQuotation;
import com.planet.vo.QuoQuotationVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Li on 2016/2/16.
 */
public interface QuoQuotationService {
    int deleteByPrimaryKey(String qid) throws Exception;

    int insert(QuoQuotation quoQuotation) throws Exception;

    int insertSelective(QuoQuotation quoQuotation) throws Exception;

    QuoQuotation selectByPrimaryKey(String qid) throws Exception;

    int updateByPrimaryKeySelective(QuoQuotation quoQuotation) throws Exception;

    int updateByPrimaryKey(QuoQuotation quoQuotation) throws Exception;

    //根据Qid查询报价单详情
    QuoQuotationVo selectByqId(String qid) throws Exception;

    //根据Qid和Poid修改报价单信息
    int updateBySelective(QuoQuotationVo quoQuotationVo) throws Exception;

    //插入报价单并查询
    QuoQuotationVo insertSelectQuo(String oid,String createId) throws Exception;

    //查询报价单（后台）
    QuoQuotationVo SelectQuo(String oid,String createId) throws Exception;
}
