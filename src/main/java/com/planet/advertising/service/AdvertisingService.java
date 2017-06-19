package com.planet.advertising.service;

import com.planet.advertising.domain.Advertising;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by aiveily on 2017/6/8.
 */
public interface AdvertisingService {

    List<Advertising> listPageAdvertising(Map map) throws Exception;

    int insertSelective(HttpServletRequest request,Advertising advertising) throws Exception;

    int updateByPrimaryKeySelective(HttpServletRequest request,Advertising advertising) throws Exception;

    List<Advertising> selectAdvertisingList(Map map) throws Exception;

    int deleteByPrimaryKey(Integer id) throws Exception;
}
