package com.planet.prodict.service;

import com.planet.prodict.domain.ProDict;
import com.planet.vo.ProDictVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/1/24.
 */
public interface ProDictService {
    int deleteByPrimaryKey(Integer did) throws Exception;

    int insert(ProDict proDict) throws Exception;

    int insertSelective(ProDict proDict,HttpServletRequest request) throws Exception;

    ProDict selectByPrimaryKey(Integer did) throws Exception;

    int updateByPrimaryKeySelective(ProDict proDict,HttpServletRequest request) throws Exception;

    int updateByPrimaryKey(ProDict proDict) throws Exception;

    List<ProDictVo> listPageProDict(Map map) throws Exception;

    //查询前缀(手机端使用)
    List<ProDict> selectPreInfo(Map map) throws Exception;

    //根据类型查对应产品的品类，品牌，系列(后台使用)
    List<ProDict> selectProall(Map<String,Object> map) throws Exception;


}
