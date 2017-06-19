package com.planet.vo;

import java.util.Date;

/**
 * Created by jyunhua li on 2016/3/3.
 */
public class MessageVo {
    private Integer mbid;

    private String message;

    private Date senddate;

    private Integer mtype;

    private Integer status;

    private Integer msgid;

    private Integer sender;

    private String receiver;

    private Integer isread;

    private String  name;

    private Integer flag;

    public Integer getMbid() {
        return mbid;
    }

    public void setMbid(Integer mbid) {
        this.mbid = mbid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSenddate() {
        return senddate;
    }

    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }

    public Integer getMtype() {
        return mtype;
    }

    public void setMtype(Integer mtype) {
        this.mtype = mtype;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMsgid() {
        return msgid;
    }

    public void setMsgid(Integer msgid) {
        this.msgid = msgid;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /*
    public String getSenddateString(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (senddate != null) {
            return df.format(senddate);
        } else {
            return null;
        }
    }

    public String getReceiverString(){
        if(flag>1){
            return "所有人";
        }else{
            return receiver;
        }
    }

    public String getNameString(){
        if(flag>1){
            return "所有人";
        }else{
            return name;
        }
    }
    */
}
