package com.planet.wechat.service;

import com.planet.wechat.Utils.CheckModel;

/**
 * Created by yehao on 2016/1/11.
 */
public interface TokenService {

    /**
     * wechat first validate
     * @param token
     * @param tokenModel
     * @return
     */
    public String validate(String token,CheckModel tokenModel) throws RuntimeException;


}
