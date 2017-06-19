package com.planet.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Li on 2016/2/15.
 */
public class OrdOrderVo {

    private String oid;

    private String rid;

    private String username;

    private String name;

    private Integer vip;

    private Integer status;

    private Date createdate;

    private String opCount;

    private String quoCount;

    private String suid;

    private int ptype;

    public int getPtype() {
        return ptype;
    }

    public void setPtype(int ptype) {
        this.ptype = ptype;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getOpCount() {
        return opCount;
    }

    public void setOpCount(String opCount) {
        this.opCount = opCount;
    }

    public String getQuoCount() {
        return quoCount;
    }

    public void setQuoCount(String quoCount) {
        this.quoCount = quoCount;
    }

    public String getCreatedateString(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (createdate != null) {
            return df.format(createdate);
        } else {
            return null;
        }
    }
    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

}
