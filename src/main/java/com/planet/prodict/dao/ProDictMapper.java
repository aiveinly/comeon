package com.planet.prodict.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.prodict.domain.ProDict;
import com.planet.vo.ProDictVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProDictMapper extends MybatisMapper{
    int deleteByPrimaryKey(Integer did);

    int insert(ProDict proDict);

    int insertSelective(ProDict proDict);

    ProDict selectByPrimaryKey(Integer did);

    int updateByPrimaryKeySelective(ProDict proDict);

    int updateByPrimaryKey(ProDict proDict);

    List<ProDictVo> listPageProDict(Map map);

    //查询前缀(手机端使用)
    List<ProDict> selectPreInfo(Map map);

    //根据类型查对应产品的品类，品牌，系列(后台使用)
    List<ProDict> selectProall(Map<String,Object> map);

    //增加字典校验是否有品类，品牌，系列重复
    String selectIsUse(Map<String,Object> param);

    //校验添加品类名称是否有重复
    int selectCategory(ProDict proDict);
}