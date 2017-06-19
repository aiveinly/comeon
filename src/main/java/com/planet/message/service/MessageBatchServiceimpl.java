package com.planet.message.service;

import com.planet.message.dao.MessageBatchMapper;
import com.planet.message.dao.MessageMapper;
import com.planet.message.domain.Message;
import com.planet.message.domain.MessageBatch;
import com.planet.user.dao.UserAgentMapper;
import com.planet.user.domain.UserAgent;
import com.planet.vo.MessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yijinjing on 16/3/1.
 */
@Service
@Transactional
public class MessageBatchServiceimpl implements MessageBatchService {

    private static final Logger logger = LoggerFactory.getLogger(MessageBatchServiceimpl.class);

    @Autowired
    private MessageBatchMapper messageBatchMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserAgentMapper userAgentMapper;

    @Autowired
    private MessageService messageService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer mbid) throws Exception {
        int i = 0;
        int j = 0;
        i = messageBatchMapper.deleteByPrimaryKey(mbid);
        if (i == 0) {
            logger.info("删除消息主表失败");
        } else if (i == 1) {
            List<Message> messages = new ArrayList<>();
            messages =  messageService.selectByMbid(mbid);
            for (Message message:messages) {
                 j =   messageMapper.deleteByPrimaryKey(message.getMsgid());
                if (j==0){
                    throw new RuntimeException("删除消息子表出错");
                }
            }
            logger.info("删除消息主表成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(MessageBatch messageBatch) throws Exception {
        int i = 0;
        i = messageBatchMapper.insert(messageBatch);
        if (i == 0) {
            logger.info("插入消息主表失败");
        } else if (i == 1) {
            logger.info("插入消息主表成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(MessageBatch messageBatch) throws Exception {
        int i = 0;
        i = messageBatchMapper.insertSelective(messageBatch);
        if (i == 0) {
            logger.info("插入消息主表失败");
        } else if (i == 1) {
            logger.info("插入消息主表成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MessageBatch selectByPrimaryKey(Integer mbid) throws Exception {
        return messageBatchMapper.selectByPrimaryKey(mbid);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(MessageBatch messageBatch) throws Exception {
        int i = 0;
        i = messageBatchMapper.updateByPrimaryKeySelective(messageBatch);
        if (i == 0) {
            logger.info("更新消息主表失败");
        } else if (i == 1) {
            logger.info("更新消息主表成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(MessageBatch messageBatch) throws Exception {
        int i = 0;
        i = messageBatchMapper.updateByPrimaryKeySelective(messageBatch);
        if (i == 0) {
            logger.info("更新消息主表失败");
        } else if (i == 1) {
            logger.info("更新消息主表成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertMessage(Map<String, Object> map) throws Exception {
        int i = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date = sdf.parse(time);
        MessageBatch messageBatch = new MessageBatch();
        Message message = new Message();
        //消息内容
        messageBatch.setMessage(String.valueOf(map.get("message")));
        //发送时间
        messageBatch.setSenddate(date);
        //消息类型
        messageBatch.setMtype(Integer.valueOf(map.get("mType").toString()));
        //消息状态(1:默认未删除)
        messageBatch.setStatus(1);
        try {
            i = messageBatchMapper.insert(messageBatch);
            if (i == 0) {
                logger.info("消息生成失败");
            } else if (i == 1) {
                logger.info("消息生成成功");
                Map<String, Object> map1 = new HashMap<>();
                MessageBatch messageBatch1 = new MessageBatch();
                messageBatch1 = messageBatchMapper.selectMaxId(map1);

                //消息主表id
                message.setMbid(messageBatch1.getMbid());
                //接收者
                message.setReceiver(map.get("receiver").toString());
                //发送者
                message.setSender(Integer.valueOf(map.get("sender").toString()));
                //设置阅读状态
                message.setIsread(0);
                int j = messageMapper.insert(message);
                if (j == 0) {
                    logger.info("插入消息从表失败");
                    return j;
                } else if (j == 1) {
                    logger.info("插入消息从表成功");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 消息群发
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertMessageAll(Map<String, Object> map) throws Exception {
        int i = 0;
        int j = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date = sdf.parse(time);
        MessageBatch messageBatch = new MessageBatch();
        Message message = new Message();
        //消息内容
        messageBatch.setMessage(String.valueOf(map.get("message")));
        //发送时间
        messageBatch.setSenddate(date);
        //消息类型
        messageBatch.setMtype(Integer.valueOf(map.get("mType").toString()));
        //消息状态(1:默认未删除)
        messageBatch.setStatus(1);
        //发送者
        message.setSender(Integer.valueOf(map.get("sender").toString()));
        //设置阅读状态
        Map<String, Object> map2 = new HashMap<>();
        List<UserAgent> userAgentList = new ArrayList<>();
        userAgentList = userAgentMapper.selectAllUser(map2);
        i = messageBatchMapper.insert(messageBatch);
        if (i == 0) {
            logger.info("消息生成失败");
        } else if (i == 1) {
            logger.info("消息生成成功");
            Map<String, Object> map1 = new HashMap<>();
            MessageBatch messageBatch1 = new MessageBatch();
            for (UserAgent UserAgent : userAgentList) {
                //接收者
                message.setReceiver(UserAgent.getUid().toString());
                messageBatch1 = messageBatchMapper.selectMaxId(map1);
                //消息主表id
                message.setMbid(messageBatch1.getMbid());
                message.setIsread(0);
                j = messageMapper.insert(message);
                if (j == 0) {
                    logger.info("插入消息从表失败");
                }
            }
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<MessageVo> listPageSelectMessage(Map<String, Object> map) throws Exception {
        return messageBatchMapper.listPageSelectMessage(map);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<MessageVo> listPageSelectForUser(Map<String, Object> map) throws Exception {
        return messageBatchMapper.listPageSelectForUser(map);
    }
}
