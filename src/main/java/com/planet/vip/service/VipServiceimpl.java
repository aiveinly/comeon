package com.planet.vip.service;

import com.planet.message.service.MessageBatchService;
import com.planet.user.domain.UserAgent;
import com.planet.user.service.UserService;
import com.planet.utils.FileOperateUtil;
import com.planet.vip.dao.VipMapper;
import com.planet.vip.domain.Vip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Li on 2016/2/24.
 */
@Service
@Transactional
public class VipServiceimpl implements VipService {

    private static final Logger logger = LoggerFactory.getLogger(VipServiceimpl.class);

    @Autowired
    private VipMapper vipMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageBatchService messageBatchService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Integer id) throws Exception {
        int i = 0;
        i = vipMapper.deleteByPrimaryKey(id);
        if (i == 0) {
            logger.info("vip申请记录删除失败");
        } else if (i == 1) {
            logger.info("vip申请记录删除成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(Vip vip) throws Exception {
        int i = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date = sdf.parse(time);
        vip.setUpdatedate(date);
        vip.setStatus(0);//申请vip未处理
        i = vipMapper.insert(vip);
        if (i == 0) {
            logger.info("插入vipApply表失败");
        } else {
            logger.info("插入vipApply表成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(Vip vip) throws Exception {
        int i = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Date date = sdf.parse(time);
        vip.setUpdatedate(date);
        vip.setStatus(0);//申请vip未处理
        i = vipMapper.insertSelective(vip);
        if (i == 0) {
            logger.info("插入vipApply表失败");
        } else {
            logger.info("插入vipApply表成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Vip selectByPrimaryKey(Integer id) throws Exception {
        return vipMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(Vip vip) throws Exception {
        int i = 0;
        int j = 0;
        int k = 0;
        i = vipMapper.updateByPrimaryKeySelective(vip);
        if (i == 0) {
            logger.info("更新vipApply表失败");
        } else if (i == 1) {
            logger.info("更新vipApply表成功");
            j = vip.getStatus();
            if (j == 1) {
                int uid = vip.getUid();
                UserAgent userAgent = new UserAgent();
                userAgent.setUid(uid);
                userAgent.setVip(1);
                if (vip.getApplytype() == 2) {
                    userAgent.setIsEngineer(1);
                } else {
                    userAgent.setIsEngineer(0);
                }
                k = userService.updateByPrimaryKeySelective(userAgent);
                if (k == 0) {
                    logger.info("更新用户表vip表出现异常");
                    throw new RuntimeException("更新用户表vip表出现异常");
                } else if (k == 1) {
                    logger.info("更新用户表vip表出现成功");
                    i = k;
                    Map<String, Object> insertMsgMap = new HashMap<>();
                    insertMsgMap.put("message", userService.selectByPrimaryKey(uid).getName() + "您的vip申请成功");
                    insertMsgMap.put("mType", "1");
                    insertMsgMap.put("receiver", uid);
                    //vip审核通过送积分
                    Map<String, Object> point = new HashMap<>();
                    int jifen = 300;
                    point.put("uid", vip.getUid());
                    point.put("message", "vip申请成功送" + jifen + "积分");
                    point.put("point", jifen);
                    point.put("level","vip");
                    userService.updatePoint(point);
                    //系统发送
                    insertMsgMap.put("sender", 0);
                    insertMsgMap.put("stStatus", 1);
                    int m = messageBatchService.insertMessage(insertMsgMap);
                    if (m == 0) {
                        logger.info("vip申请发送消息失败");
                        throw new RuntimeException("vip申请发送消息失败");
                    }
                }
            }
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(Vip vip) throws Exception {
        int i = 0;
        i = vipMapper.updateByPrimaryKey(vip);
        if (i == 0) {
            logger.info("更新vipApply表失败");
        } else if (i == 1) {
            logger.info("更新vipApply表成功");
        }
        return i;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Vip> listPageSelect(Map<String, Object> map) throws Exception {
        return vipMapper.listPageSelect(map);
    }


    /**
     * 添加vip
     *
     * @param vip
     * @return
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addVip(Vip vip) {
        return vipMapper.insertSelective(vip);
    }

    /**
     * 添加vip
     *
     * @param vip
     * @return
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addVip(Vip vip, HttpServletRequest request) {
        List list = new ArrayList();
        list = FileOperateUtil.upload(request);
        if (list != null && list.size() >= 1) {
            vip.setFile1((String) list.get(0));
        }
        return addVip(vip);
    }


}
