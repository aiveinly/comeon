package com.planet.point.service;

import com.planet.point.dao.PointLogMapper;
import com.planet.point.domain.PointLog;
import com.planet.user.dao.UserAgentMapper;
import com.planet.user.domain.UserAgent;
import org.springframework.aop.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yehao on 2016/4/13.
 */
@Service
@Transactional
public class PointServiceImpl implements PointService {

    @Autowired
    private UserAgentMapper userAgentMapper;

    @Autowired
    private PointLogMapper pointLogMapper;


    /**
     * 修改积分，并添加日志记录
     *
     * @param uid
     * @param quentity
     * @return
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int modifyPoint(int uid, int quentity, String msg, String adminname) {

        //查询user
        UserAgent userAgent = userAgentMapper.selectByPrimaryKey(uid);

        //修改积分
        userAgent.setPoint(userAgent.getPoint() + quentity);
        int success = userAgentMapper.updateByPrimaryKeySelective(userAgent);

        PointLog pointLog = new PointLog();
        if (success != 0) {
            pointLog.setUid(userAgent.getUid());
            pointLog.setAfterpoint(userAgent.getPoint());
            pointLog.setPoint(quentity);
            pointLog.setMsg(msg);
            pointLog.setTel(userAgent.getTel());
            pointLog.setCreatedate(new Date());
            pointLog.setAdminname(adminname);
            if (quentity > 0) {
                //type = 1 增加
                pointLog.setType(1);
            } else {
                //type = 2 减少
                pointLog.setType(2);
            }
            //插入记录
            success = pointLogMapper.insertSelective(pointLog);
            return success;
        }
        return 0;
    }

    /**
     * 分页查询积分日志
     *
     * @param map
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<PointLog> searchPointLogs(Map map) {
        return pointLogMapper.listPagePointLog(map);
    }
}
