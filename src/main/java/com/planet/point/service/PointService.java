package com.planet.point.service;

import com.planet.point.domain.PointLog;

import java.util.List;
import java.util.Map;

/**
 * Created by yehao on 2016/4/13.
 */
public interface PointService {


    /**
     * 修改积分，并添加日志记录
     *
     * @param uid
     * @param quentity
     * @return
     */
    int modifyPoint(int uid, int quentity, String msg,String adminname) throws Exception;

    /**
     * 分页查询积分日志
     *
     * @param map
     * @return
     */
    List<PointLog> searchPointLogs(Map map);
}
