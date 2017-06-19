package com.planet.user.service;

import com.planet.user.domain.UserAgent;

import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/1/19.
 */
public interface UserService {

    int deleteByPrimaryKey(Integer uid);

    int insert(UserAgent record) throws Exception;

    int insertSelective(UserAgent record) throws Exception;

    UserAgent selectByPrimaryKey(Integer uid) throws Exception;

    int updateByPrimaryKeySelective(UserAgent record) throws Exception;

    int updateByPrimaryKey(UserAgent record) throws Exception;

    List<UserAgent> listPageselectUserAgent(Map map) throws Exception;

    //根据手机号查询用户信息
    UserAgent selectByUserName(String username) throws Exception;

    //根据用户id 更新积分，并推送系统消息做记录
    int updatePoint(Map<String, Object> map) throws Exception;

    List<Map> selectAllUserAgentToVo(Map<String, Object> map) throws Exception;

    UserAgent selectUserAgentByCode(String referralcode)throws Exception;

    List<UserAgent> listPageUserAgentAndReferralUname(Map map) throws Exception;
}
