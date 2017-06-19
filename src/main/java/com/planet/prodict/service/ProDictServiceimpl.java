package com.planet.prodict.service;

import com.planet.prodict.dao.ProDictMapper;
import com.planet.prodict.domain.ProDict;
import com.planet.proproduct.service.ProProductService;
import com.planet.utils.FileOperateUtil;
import com.planet.vo.ProDictVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/1/24.
 */
@Service
@Transactional
public class ProDictServiceimpl implements ProDictService {
    private static final Logger logger = LoggerFactory.getLogger(ProDictServiceimpl.class);

    @Autowired
    private ProDictMapper proDictMapper;

    @Autowired
    private ProProductService proProductService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer did) throws Exception {
        int i = 0;
//        Map<String,Object> map = new HashMap<>();
//        ProDict proDict = new ProDict();
//        proDict =  selectByPrimaryKey(did);
//        if(proDict.getType()==1){
//                map.put("sortid",did);
//        }else if(proDict.getType()==2){
//            map.put("brandid",did);
//        }else if(proDict.getType()==3){
//            map.put("seriesid",did);
//        }
//        int j = proProductService.selectById(map);
//        if(j==0){
//          i =  proDictMapper.deleteByPrimaryKey(did);
//        }else if(j>0){
//            i=0;
//        }
        Map<String, Object> param = new HashMap<>();
        param.put("did", did);
        param.put("ret_out", "");
        proDictMapper.selectIsUse(param);
        int ret_out = Integer.valueOf(param.get("ret_out").toString()) ;
        if (ret_out == 0) {
            i = proDictMapper.deleteByPrimaryKey(did);
        } else if (ret_out > 0) {
            i = 2;//状态为2 表示有关联，无法删除
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(ProDict proDict) throws Exception {
        int i = 0;
        i = proDictMapper.insert(proDict);
        if (i == 0) {
            logger.info("插入失败");
        } else if (i == 1) {
            logger.info("插入成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(ProDict proDict, HttpServletRequest request) throws Exception {
        int i = 0;
        int j = 0;
        List list = new ArrayList();
        j = proDictMapper.selectCategory(proDict);
        if (j == 0) {
            list = FileOperateUtil.upload(request);
            if (list.size() > 0) {
                proDict.setImgurl(list.get(0).toString());
            }
            i = proDictMapper.insertSelective(proDict);
            if (i == 0) {
                logger.info("插入失败");
            } else if (i == 1) {
                logger.info("插入成功");
            }
        } else if (j > 0) {
            i = 0;
            logger.info("已存在");
            return i;
        }







        /*
        if (proDict.getType() == 1) {
            j = proDictMapper.selectCategory(proDict.getDictname());
            if (j == 0) {
                list = FileOperateUtil.upload(request);
                if (list.size() > 0) {
                    proDict.setImgurl(list.get(0).toString());
                }
                i = proDictMapper.insertSelective(proDict);
                if (i == 0) {
                    logger.info("插入失败");
                } else if (i == 1) {
                    logger.info("插入成功");
                }
            } else if (j > 0) {
                i = 0;
                logger.info("已存在");
                return i;
            }
        }else if(proDict.getType() == 2|proDict.getType() == 3){
            list = FileOperateUtil.upload(request);
            if (list.size() > 0) {
                proDict.setImgurl(list.get(0).toString());
            }else{
                proDict.setImgurl(null);
            }
            i = proDictMapper.insertSelective(proDict);
            if (i == 0) {
                logger.info("插入失败");
            } else if (i == 1) {
                logger.info("插入成功");
            }
        }

        */




        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProDict selectByPrimaryKey(Integer did) throws Exception {
        return proDictMapper.selectByPrimaryKey(did);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(ProDict proDict, HttpServletRequest request) throws Exception {
        int i = 0;
        List list = new ArrayList();
        list = FileOperateUtil.upload(request);
        if (list.size() > 0) {
            proDict.setImgurl(list.get(0).toString());
            i = proDictMapper.updateByPrimaryKeySelective(proDict);
        }else if(list.size()==0){
            i = proDictMapper.updateByPrimaryKeySelective(proDict);
        }
        if (i == 0) {
            logger.info("更新失败");
        } else if (i == 1) {
            logger.info("更新成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(ProDict proDict) throws Exception {
        int i = 0;
        i = proDictMapper.updateByPrimaryKey(proDict);
        if (i == 0) {
            logger.info("更新失败");
        } else if (i == 1) {
            logger.info("更新成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<ProDictVo> listPageProDict(Map map) throws Exception {
        List<ProDictVo> proDicts = new ArrayList<>();
        proDicts = proDictMapper.listPageProDict(map);
        return proDicts;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<ProDict> selectPreInfo(Map map) throws Exception {
        List<ProDict> proDicts = new ArrayList<>();
        proDicts = proDictMapper.selectPreInfo(map);
        return proDicts;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<ProDict> selectProall(Map map) throws Exception {
        return proDictMapper.selectProall(map);
    }

}
