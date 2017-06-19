package com.planet.admin.service;

import com.planet.admin.domain.Admin;

import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/2/25.
 */
public interface AdminService {
    int deleteByPrimaryKey(Integer suid) throws Exception;

    int insert(Admin admin) throws Exception;

    int insertSelective(Admin admin) throws Exception;

    Admin selectByPrimaryKey(Integer suid) throws Exception;

    int updateByPrimaryKeySelective(Admin admin) throws Exception;

    int updateByPrimaryKey(Admin admin) throws Exception;

    //分页查询
    List<Admin> listPageSelect(Map<String,Object> map) throws Exception;

    //根据姓名查询管理员实体
    Admin selectByUserName(String username) throws Exception;
}
