package com.planet.user.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.user.domain.UserAgent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserAgentMapper extends MybatisMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(UserAgent record);

    int insertSelective(UserAgent record);

    UserAgent selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(UserAgent record);

    int updateByPrimaryKey(UserAgent record);

    List<UserAgent> listPageselectUserAgent(Map map);

    //根据手机号查询用户信息
    UserAgent selectByUserName(String username);

    //查询全部用户id
    List<UserAgent> selectAllUser(Map<String,Object> map);

    //根据推荐码查询实体
    UserAgent selectByReferralCode(String referralcode);

    /**
     * 查询用户信息带有该用户注册时的邀请码所属人
     * @return
     */
    List<UserAgent> listPageUserAgentAndReferralUname(Map map);
}