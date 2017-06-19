package com.planet.message.service;

import com.planet.message.dao.MessageMapper;
import com.planet.message.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yijinjing on 16/3/1.
 */
@Service
@Transactional
public class MessageServiceimpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceimpl.class);

    @Autowired
    private MessageMapper messageMapper;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer msgid) throws Exception {
        int i = 0;
        i = messageMapper.deleteByPrimaryKey(msgid);
        if(i==0){
            logger.info("删除消息字表记录失败");
        }else if(1==0){
            logger.info("删除消息字表记录成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(Message message) throws Exception {
        int i = 0;
        i = messageMapper.insert(message);
        if(i==0){
            logger.info("删除消息字表记录失败");
        }else if(1==0){
            logger.info("删除消息字表记录成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(Message message) throws Exception {
        int i = 0;
        i = messageMapper.insertSelective(message);
        if(i==0){
            logger.info("删除消息字表记录失败");
        }else if(1==0){
            logger.info("删除消息字表记录成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Message selectByPrimaryKey(Integer msgid) throws Exception {
        return messageMapper.selectByPrimaryKey(msgid);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(Message message) throws Exception {
        int i = 0;
        i = messageMapper.updateByPrimaryKeySelective(message);
        if(i==0){
            logger.info("更新消息字表记录失败");
        }else if(1==0){
            logger.info("更新消息字表记录成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(Message message) throws Exception {
        int i = 0;
        i = messageMapper.updateByPrimaryKey(message);
        if(i==0){
            logger.info("更新消息字表记录失败");
        }else if(1==0){
            logger.info("更新消息字表记录成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Message> selectByMbid(Integer mbid) throws Exception {
        return messageMapper.selectByMbid(mbid);
    }
}
