package com.planet.log.service;

import com.planet.log.dao.LogMapper;
import com.planet.log.domain.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by Li on 2016/2/23.
 */
@Service
@Transactional
public class LogServiceimpl implements LogService{

    private static final Logger logger = LoggerFactory.getLogger(LogServiceimpl.class);

    @Autowired
    private LogMapper logMapper;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer logid) throws Exception {
        int i = 0;
        i = logMapper.deleteByPrimaryKey(logid);
        if(i==0){
            logger.info("删除日志失败");
        }else if(i==1){
            logger.info("删除日志成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(Log log) throws Exception {
        return logMapper.insert(log);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(Log log) throws Exception {
        return logMapper.insert(log);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Log selectByPrimaryKey(Integer logid) throws Exception {
        return logMapper.selectByPrimaryKey(logid);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(Log log) throws Exception {
        int i = 0;
        i = logMapper.updateByPrimaryKeySelective(log);
        if(i==0){
            logger.info("更新日志失败");
        }else if(i==1){
            logger.info("更新日志成功");
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(Log log) throws Exception {
        int i = 0;
        i = logMapper.updateByPrimaryKey(log);
        if(i==0){
            logger.info("更新日志失败");
        }else if(i==1){
            logger.info("更新日志成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Log listPageselectByMap(Map<String, Object> map) throws Exception {
        return logMapper.listPageselectByMap(map);
    }
}
