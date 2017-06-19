package com.planet.quoquotation.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.quoquotation.domain.QuoQuotation;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoQuotationMapper extends MybatisMapper{
    int deleteByPrimaryKey(String qid);

    int insert(QuoQuotation quoQuotation);

    int insertSelective(QuoQuotation quoQuotation);

    QuoQuotation selectByPrimaryKey(String qid);

    int updateByPrimaryKeySelective(QuoQuotation quoQuotation);

    int updateByPrimaryKey(QuoQuotation quoQuotation);
}