package com.planet.message.dao;

import com.planet.common.mybatis.MybatisMapper;
import com.planet.message.domain.MessageBatch;
import com.planet.vo.MessageVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MessageBatchMapper extends MybatisMapper{
    int deleteByPrimaryKey(Integer mbid);

    int insert(MessageBatch messageBatch);

    int insertSelective(MessageBatch messageBatch);

    MessageBatch selectByPrimaryKey(Integer mbid);

    int updateByPrimaryKeySelective(MessageBatch messageBatch);

    int updateByPrimaryKey(MessageBatch messageBatch);

    //查询消息主表id
    MessageBatch selectMaxId(Map<String,Object> map);

    //消息分页模糊查询
    List<MessageVo> listPageSelectMessage(Map<String,Object> map);

    //消息分页查询（ for customer）
    List<MessageVo> listPageSelectForUser(Map<String,Object> map);
}