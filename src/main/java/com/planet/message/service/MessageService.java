package com.planet.message.service;

import com.planet.message.domain.Message;

import java.util.List;

/**
 * Created by yijinjing on 16/3/1.
 */
public interface MessageService {
    int deleteByPrimaryKey(Integer msgid) throws Exception;

    int insert(Message message) throws Exception;

    int insertSelective(Message message) throws Exception;

    Message selectByPrimaryKey(Integer msgid) throws Exception;

    int updateByPrimaryKeySelective(Message message) throws Exception;

    int updateByPrimaryKey(Message message) throws Exception;

    //查询所有mbid用于删除
    List<Message> selectByMbid(Integer mbid) throws Exception;
}
