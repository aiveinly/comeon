package com.planet.vo;

import java.io.Serializable;

/**
 * Created by junhua on 2016/3/29.
 * 微信凭证
 */
public class Token implements Serializable {

    private static final long serialVersionUID = -1052482034453320604L;

    // 接口访问凭证
    private String accessToken;

    // 凭证有效期，单位：秒
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
