package com.planet.user.service;

import com.planet.common.util.DateTools;
import com.planet.message.service.MessageBatchService;
import com.planet.user.dao.UserAgentMapper;
import com.planet.user.domain.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Li on 2016/1/19.
 */
@Service
@Transactional
public class UserServiceimpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceimpl.class);

    @Autowired
    private UserAgentMapper userAgentMapper;

    @Autowired
    private MessageBatchService messageBatchService;


    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer uid) {
        int i = 0;
        i = userAgentMapper.deleteByPrimaryKey(uid);
        if (i==0){
            logger.info("删除用户失败");
        }else if (i==1){
            logger.info("删除用户成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(UserAgent userAgent) throws Exception {
        int i = 0;
        boolean flag = true;
        String referralcode = null;
        //通过下面的方法生成唯一的推荐码
        while(flag){
            UserAgent userAgent1  = null;
            referralcode =  generateWord();//生成4位随机数
            userAgent1 =  userAgentMapper.selectByReferralCode(referralcode);
            if(userAgent1==null){
                logger.info("生成推荐码"+referralcode);
                break;

            }
        }
        userAgent.setReferralcode(referralcode);
        i = userAgentMapper.insert(userAgent);
        if (i == 0) {
            logger.info("插入失败");
        } else if (i == 1) {
            logger.info("插入成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(UserAgent userAgent) throws Exception {
        int i = 0;
        boolean flag = true;
        String referralcode = null;
        //通过下面的方法生成唯一的推荐码
        while(flag){
            UserAgent userAgent1  = null;
            referralcode =  generateWord().toUpperCase();//生成4位随机数
            userAgent1 =  userAgentMapper.selectByReferralCode(referralcode);
            if(userAgent1==null){
                logger.info("推荐码"+referralcode);
                break;
            }
        }
        userAgent.setReferralcode(referralcode);
        i = userAgentMapper.insertSelective(userAgent);
        if (i == 0) {
            logger.info("插入失败");
        } else if (i == 1) {
            logger.info("插入成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserAgent selectByPrimaryKey(Integer uid) throws Exception {
        return   userAgentMapper.selectByPrimaryKey(uid);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(UserAgent userAgent) throws Exception {
        int i = 0;
        i = userAgentMapper.updateByPrimaryKeySelective(userAgent);
        if (i == 0) {
            logger.info("更新失败");
        } else if (i == 1) {
            logger.info("更新成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(UserAgent userAgent) throws Exception {
        int i = 0;
        i = userAgentMapper.updateByPrimaryKey(userAgent);
        if (i == 0) {
            logger.info("更新失败");
        } else if (i == 1) {
            logger.info("更新成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<UserAgent> listPageselectUserAgent(Map map) {
        List<UserAgent> userAgents=null;
        userAgents=userAgentMapper.listPageselectUserAgent(map);
        return userAgents;
    }

    /**
     * 根据手机号查询用户信息
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserAgent selectByUserName(String username) throws Exception {
        return userAgentMapper.selectByUserName(username);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updatePoint(Map<String, Object> map) throws Exception {
        UserAgent userAgent = new UserAgent();
        Map<String,Object> info = new HashMap<>();
        int i = 0;

        // VIP通过送积分
        if (map.get("level").toString()=="vip"){
            int uid = Integer.valueOf(map.get("uid").toString());
            String message = map.get("message").toString();
            int point = map.get("point").toString()!=null?Integer.parseInt(map.get("point").toString()):0;
            userAgent = userAgentMapper.selectByPrimaryKey(uid);
            point = userAgent.getPoint()+point;//对积分进行处理
            userAgent.setPoint(point);
            i = userAgentMapper.updateByPrimaryKey(userAgent);

            if(i==0){
                return i;
            }else if(i==1){
                info.put("message",message);
                info.put("mType",1);
                info.put("receiver",uid);
                info.put("sender",0);//0:系统发送
                i =  messageBatchService.insertMessage(info);
                if(i==0){
                    throw new RuntimeException("推送消息异常");
                }
            }
        }

        //注册送积分
        if (map.get("level").toString()=="register"){

            //  String referralcode = null;//推荐码
            int uid = Integer.valueOf(map.get("uid").toString());
            //  String message = map.get("message").toString();
            int point = map.get("point").toString()!=null?Integer.parseInt(map.get("point").toString()):0;
            userAgent = userAgentMapper.selectByPrimaryKey(uid);
            point = userAgent.getPoint()+point;//对积分进行处理
            userAgent.setPoint(point);
            i = userAgentMapper.updateByPrimaryKey(userAgent);
            if (i==1){
                info.put("message","用户注册赠送"+String.valueOf(point)+"积分");
                info.put("mType",1);
                info.put("receiver",uid);
                info.put("sender",0);//0:系统发送
                i =  messageBatchService.insertMessage(info);

            }
            // 推荐人增加积分
            String  referralcode = map.get("referralcode").toString();
            point = 10;
            if(referralcode!=null&&!"".equals(referralcode)){
                UserAgent userAgent1  = null;
                userAgent1 =  userAgentMapper.selectByReferralCode(referralcode);
                if (userAgent1!=null) {
                    userAgent1.setPoint( userAgent1.getPoint()+point);
                    i = userAgentMapper.updateByPrimaryKeySelective(userAgent1);
                    info.put("message","用户使用推荐码增加"+String.valueOf(point)+"积分");
                    info.put("mType",1);
                    info.put("receiver",userAgent1.getUid());
                    info.put("sender",0);//0:系统发送
                    i =  messageBatchService.insertMessage(info);

                }

            }
        }


        return i;
    }

    @Override
    public List<Map> selectAllUserAgentToVo(Map<String, Object> map) throws Exception {
        List<Map> userAgentVoList = new ArrayList<>();
        List<UserAgent> userAgent = userAgentMapper.selectAllUser(map);
        if(null!=userAgent&&userAgent.size()>0){
            for(UserAgent ua :userAgent){
                Map vo=new HashMap();
                vo.put("username",ua.getUsername());
                vo.put("name", ua.getName());
                vo.put("vip",ua.getVip() == 1 ? "是" : "否");
                vo.put("point",ua.getPoint());
                vo.put("tel",ua.getTel());
                vo.put("status",ua.getStatus()==1?"正常":"停用");
                vo.put("remark",ua.getRemark());
                vo.put("logindate",null!= ua.getLogindate() ? DateTools.turnJavaUtilDateToStrDate(ua.getLogindate(), "yyyy-MM-dd hh:mm:ss EE") :"");
                userAgentVoList.add(vo);
            }
        }
        return userAgentVoList;
    }

    @Override
    public UserAgent selectUserAgentByCode(String referralcode) throws Exception {
        return userAgentMapper.selectByReferralCode(referralcode);
    }

    @Override
    public List<UserAgent> listPageUserAgentAndReferralUname(Map map) throws Exception {
        return userAgentMapper.listPageUserAgentAndReferralUname(map);
    }


    //生成4位随机数
    private String generateWord() {
        String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",
                "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z" };
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(4, 8);
        logger.info("推荐码"+result);
        return result;
    }
}
