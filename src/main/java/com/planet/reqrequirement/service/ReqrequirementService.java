package com.planet.reqrequirement.service;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.reqrequirement.domain.ReqRequirement;
import com.planet.vo.ReqrequirementVo;

import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/1/21.
 */

public interface ReqrequirementService  {
    int deleteByPrimaryKey(String rid) throws Exception;

    int insert(ReqRequirement reqRequirement) throws Exception;

    int insertSelective(ReqRequirement reqRequirement) throws Exception;

    ReqRequirement selectByPrimaryKey(String rid) throws Exception;

    int updateByPrimaryKeySelective(ReqRequirement reqRequirement) throws Exception;

    int updateByPrimaryKey(ReqRequirement reqRequirement) throws Exception;

    //需求查询列表（后台）
    List<ReqRequirement> listPagereqRequirements(Map<String,Object> map) throws Exception;

    //需求详情查询（后台）
    ReqrequirementVo selectByRid(String Rid) throws Exception;
}
