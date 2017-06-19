package com.planet.advertising.service;

import com.planet.advertising.dao.AdvertisingMapper;
import com.planet.advertising.domain.Advertising;
import com.planet.utils.FileOperateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aiveily on 2017/6/8.
 */
@Service
public class AdvertisingServiceimpl implements AdvertisingService {

    private static final Logger logger = LoggerFactory.getLogger(AdvertisingServiceimpl.class);

    @Autowired
    private AdvertisingMapper advertisingMapper;

    @Override
    public List<Advertising> listPageAdvertising(Map map) {
        return advertisingMapper.listPageAdvertising(map);
    }

    @Override
    public int insertSelective(HttpServletRequest request, Advertising advertising) throws Exception{
        int result=0;
        List list = FileOperateUtil.upload(request);
        if (list.size() > 0) {
            advertising.setImgurl(list.get(0).toString());
        }
        result = advertisingMapper.insertSelective(advertising);
        if (result == 0) {
            logger.info("插入失败");
        } else if (result == 1) {
            logger.info("插入成功");
        }
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(HttpServletRequest request, Advertising advertising) throws  Exception{
        int i = 0;
        List list = FileOperateUtil.upload(request);
        if (list.size() > 0) {
            advertising.setImgurl(list.get(0).toString());
        }
        i = advertisingMapper.updateByPrimaryKeySelective(advertising);
        if (i == 0) {
            logger.info("更新失败");
        } else if (i == 1) {
            logger.info("更新成功");
        }
        return i;
    }

    @Override
    public List<Advertising> selectAdvertisingList(Map map) throws Exception{
        return advertisingMapper.selectAdvertisingList(map);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) throws Exception{
        return advertisingMapper.deleteByPrimaryKey(id);
    }
}
