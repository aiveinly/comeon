package com.planet.test.service;

import com.planet.common.mybatis.plugins.page.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by yehao on 2016/1/13.
 */
public interface TestService {

    int addId();

    List<Integer> searchAllId(Map map);
}
