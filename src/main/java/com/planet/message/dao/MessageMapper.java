package com.planet.message.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.message.domain.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper extends MybatisMapper{
    int deleteByPrimaryKey(Integer msgid);

    int insert(Message message);

    int insertSelective(Message message);

    Message selectByPrimaryKey(Integer msgid);

    int updateByPrimaryKeySelective(Message message);

    int updateByPrimaryKey(Message message);

    //查询所有mbid用于删除
    List<Message> selectByMbid(Integer mbid);
}