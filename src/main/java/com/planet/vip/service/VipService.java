package com.planet.vip.service;

import com.planet.vip.domain.Vip;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/2/24.
 */
public interface VipService {
    int deleteByPrimaryKey(Integer id) throws Exception;

    int insert(Vip vip) throws Exception;

    int insertSelective(Vip vip) throws Exception;

    Vip selectByPrimaryKey(Integer id) throws Exception;

    int updateByPrimaryKeySelective(Vip vip) throws Exception;

    int updateByPrimaryKey(Vip vip) throws Exception;

    //分页查询
    List<Vip> listPageSelect(Map<String,Object> map) throws Exception;

    /**
     * 添加vip
     * @param vip
     * @return
     */
    int addVip(Vip vip);

    /**
     * 添加vip
     * @param vip
     * @return
     */
    int addVip(Vip vip, HttpServletRequest request);
}
