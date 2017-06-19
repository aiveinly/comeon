package com.planet.admin.service;

import com.planet.admin.dao.AdminMapper;
import com.planet.admin.domain.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/2/25.
 */
@Service
@Transactional
public class AdminServiceimpl implements AdminService{

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceimpl.class);

    @Autowired
    private AdminMapper adminMapper;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer suid) throws Exception {
        int i = 0;
        i = adminMapper.deleteByPrimaryKey(suid);
        if(i==0){
            logger.info("删除管理员失败");
        }else if(i==1){
            logger.info("删除管理员成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(Admin admin) throws Exception {
        int i = 0;
        i = adminMapper.insert(admin);
        if(i==0){
            logger.info("创建管理员失败");
        }else if(i==1){
            logger.info("创建管理员成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(Admin admin) throws Exception {
        int i = 0;
        i = adminMapper.insertSelective(admin);
        if(i==0){
            logger.info("创建管理员失败");
        }else if(i==1){
            logger.info("创建管理员成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Admin selectByPrimaryKey(Integer suid) throws Exception {
        return adminMapper.selectByPrimaryKey(suid);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(Admin admin) throws Exception {
        int i = 0;
        i = adminMapper.updateByPrimaryKeySelective(admin);
        if(i==0){
            logger.info("更新管理员失败");
        }else if(i==1){
            logger.info("更新管理员成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(Admin admin) throws Exception {
        int i = 0;
        i = adminMapper.updateByPrimaryKey(admin);
        if(i==0){
            logger.info("更新管理员失败");
        }else{
            logger.info("更新管理员成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Admin> listPageSelect(Map<String, Object> map) throws Exception {
        return adminMapper.listPageSelect(map);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Admin selectByUserName(String username) throws Exception {
        return adminMapper.selectByUserName(username);
    }
}
