package com.planet.config.service;

import com.planet.config.dao.ConfigMapper;
import com.planet.config.domain.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Li on 2016/2/25.
 */
@Service
@Transactional
public class ConfigServicrimpl implements ConfigService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigServicrimpl.class);

    @Autowired
    private ConfigMapper configMapper;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer id) throws Exception {
        return 0;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(Config config) throws Exception {
        int i = 0;
        i = configMapper.insert(config);
        if(i==0){
            logger.info("插入配置失败");
        }else if(i==1){
            logger.info("插入配置成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(Config config) throws Exception {
        int i = 0;
        i = configMapper.insert(config);
        if(i==0){
            logger.info("插入配置失败");
        }else if(i==1){
            logger.info("插入配置成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Config selectByPrimaryKey(Integer id) throws Exception {
        return configMapper.selectByPrimaryKey(1);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(Config config) throws Exception {
        int i = 0;
        i = configMapper.updateByPrimaryKeySelective(config);
        if(i==0){
            logger.info("更新配置失败");
        }else if(i==1){
            logger.info("更新配置成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(Config config) throws Exception {
        int i = 0;
        i = configMapper.updateByPrimaryKeySelective(config);
        if(i==0){
            logger.info("更新配置失败");
        }else if(i==1){
            logger.info("更新配置成功");
        }
        return i;
    }
}
