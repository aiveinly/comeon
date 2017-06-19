package com.planet.suggest.service;

import com.planet.message.service.MessageBatchService;
import com.planet.suggest.dao.SuggestMapper;
import com.planet.suggest.domain.Suggest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Li on 2016/2/24.
 */
@Service
@Transactional
public class SuggestServiceimpl implements SuggestService{

    private static final Logger logger = LoggerFactory.getLogger(SuggestServiceimpl.class);

    @Autowired
    private SuggestMapper suggestMapper;

    @Autowired
    private MessageBatchService messageBatchService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer sgid) throws Exception {
        int i = 0;
        i = suggestMapper.deleteByPrimaryKey(sgid);
        if(i==0){
            logger.info("删除建议失败");
        }else if(i==1){
            logger.info("删除建议成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(Suggest suggest) throws Exception {
        int i = 0;
        i = suggestMapper.insert(suggest);
        if (i==0){
            logger.info("删除建议失败");
        }else if (i==1){
            logger.info("删除建议成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(Suggest suggest) throws Exception {
        int i = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date =sdf.parse(time);
        suggest.setUpdatedate(date);
        suggest.setStatus(0);
        i = suggestMapper.insertSelective(suggest);
        if(i==0){
            logger.info("插入建议失败");
        }else if(i==1){
            logger.info("插入建议成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Suggest selectByPrimaryKey(Integer sgid) throws Exception {
        return suggestMapper.selectByPrimaryKey(sgid);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(Suggest suggest) throws Exception {
        int i = 0;
        i = suggestMapper.updateByPrimaryKeySelective(suggest);
        if(i==0){
            logger.info("更新建议失败");
        }else if(i==1){
            logger.info("更新建议成功");
            if (suggest.getStatus()==1){
                Map<String,Object> insertMsgMap = new HashMap<>();
                insertMsgMap.put("message","您的建议已受理！");
                insertMsgMap.put("mType","0");
                insertMsgMap.put("receiver",suggest.getUid());//可能存在获取不到用户id的情况
                insertMsgMap.put("sender",0);
                insertMsgMap.put("stStatus",1);
                int m = messageBatchService.insertMessage(insertMsgMap);
                if(m==0){
                    logger.info("报价单生成发送消息失败");
                    throw new RuntimeException("报价单生成发送消息失败");
                }
            }
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(Suggest suggest) throws Exception {
       int i = 0;
        i = suggestMapper.updateByPrimaryKey(suggest);
        if (i==0){
            logger.info("更新建议失败");
        }else if (i==1){
            logger.info("更新建议成功");
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Suggest> listPageSelect(Map<String, Object> map) throws Exception {
        return suggestMapper.listPageSelect(map);
    }
}
