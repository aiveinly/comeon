package com.planet.vo;

import com.planet.user.controller.QuoPriceVo;

import java.util.List;

/**
 * Created by yjj on 2016/3/1.
 */
public class QuoQuoVo {
    private int iid;
    private String jianyi;
    private List<QuoPriceVo> ordpreorderid;
    private String qid;
    private int uid;

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getJianyi() {
        return jianyi;
    }

    public void setJianyi(String jianyi) {
        this.jianyi = jianyi;
    }

    public List getOrdpreorderid() {
        return ordpreorderid;
    }

    public void setOrdpreorderid(List ordpreorderid) {
        this.ordpreorderid = ordpreorderid;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
