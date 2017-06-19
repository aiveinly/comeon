package com.planet.sysfile.service;

import com.planet.sysfile.domain.SysFile;

import java.util.List;

/**
 * Created by Li on 2016/1/30.
 */
public interface SysFileService {
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(SysFile sysFile) throws Exception;

    int insert(SysFile sysFile, List<String>urllist) throws Exception;

    int insertSelective(SysFile sysFile) throws Exception;

    SysFile selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(SysFile sysFile) throws Exception;

    int updateByPrimaryKey(SysFile sysFile) throws Exception;

    //根据fileid 查询实体
    List<SysFile> selectByFileId(Integer fileid) throws Exception;
}
