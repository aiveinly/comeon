package com.planet.message.service;


import com.planet.message.domain.MessageBatch;
import com.planet.vo.MessageVo;

import java.util.List;
import java.util.Map;

/**
 * Created by yijinjing on 16/3/1.
 */

public interface MessageBatchService  {
    int deleteByPrimaryKey(Integer mbid) throws Exception;

    int insert(MessageBatch messageBatch) throws Exception;

    int insertSelective(MessageBatch messageBatch) throws Exception;

    MessageBatch selectByPrimaryKey(Integer mbid) throws Exception;

    int updateByPrimaryKeySelective(MessageBatch messageBatch) throws Exception;

    int updateByPrimaryKey(MessageBatch messageBatch) throws Exception;

    //消息发送
    int insertMessage(Map<String,Object> map) throws Exception;

    //消息群发
    int insertMessageAll(Map<String,Object> map) throws Exception;

    //消息分页模糊查询
    List<MessageVo> listPageSelectMessage(Map<String,Object> map) throws Exception;

    //消息分页查询（ for customer）
    List<MessageVo> listPageSelectForUser(Map<String,Object> map) throws Exception;

}
