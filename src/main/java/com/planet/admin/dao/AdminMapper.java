package com.planet.admin.dao;

import com.planet.admin.domain.Admin;
import com.planet.common.mybatis.MybatisMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdminMapper extends MybatisMapper{
    int deleteByPrimaryKey(Integer suid);

    int insert(Admin admin);

    int insertSelective(Admin admin);

    Admin selectByPrimaryKey(Integer suid);

    int updateByPrimaryKeySelective(Admin admin);

    int updateByPrimaryKey(Admin admin);

    //分页查询
    List<Admin> listPageSelect(Map<String,Object> map);

    //根据姓名查询管理员实体
    Admin selectByUserName(String username);
}