package com.planet.sysfile.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.sysfile.domain.SysFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysFileMapper extends MybatisMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(SysFile sysFile);

    int insertSelective(SysFile sysFile);

    SysFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysFile sysFile);

    int updateByPrimaryKey(SysFile sysFile);

    //根据fileid 查询实体
    List<SysFile> selectByFileId(Integer fileid);

}