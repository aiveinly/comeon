package com.planet.sysfile.service;

import com.planet.idrule.service.IdRuleService;
import com.planet.sysfile.dao.SysFileMapper;
import com.planet.sysfile.domain.SysFile;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Li on 2016/1/30.
 */
@Service
@Transactional
public class SysFileServiceimpl implements SysFileService {

    private static final Logger logger = LoggerFactory.getLogger(SysFileServiceimpl.class);

    @Autowired
    private SysFileMapper sysFileMapper;

    @Autowired
    private IdRuleService idRuleService;


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer id)throws Exception {
        int i = 0;
        i = sysFileMapper.deleteByPrimaryKey(id);
        if (i==0){
            logger.info("删除文件失败");
        }else if (i==1){
            logger.info("删除文件成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(SysFile sysFile)throws Exception {
        int i = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date =sdf.parse(time);
        sysFile.setCreatetime(date);
        i = sysFileMapper.insert(sysFile);
        if (i == 0) {
            logger.info("插入失败");
        } else if (i == 1) {
            logger.info("插入成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(SysFile sysFile, List<String> urllist)throws Exception {
        int i = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date =sdf.parse(time);
        sysFile.setCreatetime(date);
        String num =idRuleService.selectByNumName("File").substring(4);
        sysFile.setFileid(Integer.valueOf(num));
        int size = urllist.size();
        for (int j = 0; j <size ; j++) {
            sysFile.setFileurl(urllist.get(j));
            i = sysFileMapper.insert(sysFile);
            if (i == 0) {
                logger.info("插入失败");
            } else if (i == 1) {
                logger.info("插入成功");
            }
        }
        return Integer.valueOf(num);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(SysFile sysFile)throws Exception {
        int i = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date =sdf.parse(time);
        sysFile.setCreatetime(date);
        i = sysFileMapper.insertSelective(sysFile);
        if (i == 0) {
            logger.info("插入失败");
        } else if (i == 1) {
            logger.info("插入成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SysFile selectByPrimaryKey(Integer id)throws Exception {
        return sysFileMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(SysFile sysFile)throws Exception {
        int i = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date =sdf.parse(time);
        sysFile.setCreatetime(date);
        i = sysFileMapper.updateByPrimaryKeySelective(sysFile);
        if (i == 0) {
            logger.info("更新失败");
        } else if (i == 1) {
            logger.info("更新成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(SysFile sysFile)throws Exception {
        int i = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date =sdf.parse(time);
        sysFile.setCreatetime(date);
        i = sysFileMapper.updateByPrimaryKey(sysFile);
        if (i == 0) {
            logger.info("更新失败");
        } else if (i == 1) {
            logger.info("更新成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SysFile> selectByFileId(Integer fileid) throws Exception {
        List<SysFile> sysFiles = new ArrayList<>();
        sysFiles=sysFileMapper.selectByFileId(fileid);
        return sysFiles;
    }
}
