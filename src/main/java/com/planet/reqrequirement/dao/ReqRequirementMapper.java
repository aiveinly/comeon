package com.planet.reqrequirement.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.reqrequirement.domain.ReqRequirement;
import com.planet.vo.ReqrequirementVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReqRequirementMapper extends MybatisMapper{
    int deleteByPrimaryKey(String rid);

    int insert(ReqRequirement reqRequirement);

    int insertSelective(ReqRequirement reqRequirement);

    ReqRequirement selectByPrimaryKey(String rid);

    int updateByPrimaryKeySelective(ReqRequirement reqRequirement);

    int updateByPrimaryKey(ReqRequirement reqRequirement);

    //需求查询列表（后台）
    List<ReqRequirement> listPagereqRequirements(Map<String,Object> map);


}