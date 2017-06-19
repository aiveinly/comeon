package com.planet.vip.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.std.StdArraySerializers;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties
public class Vip {
    private Integer id;

    private Integer uid;

    private String username;

    private String content;

    private String fileurl;

    private Integer status;

    private Integer aprovalid;

    private Date updatedate;

    private String truename;

    private String tel;

    private String knowbrand;

    private String area;

    private Integer workdate;

    private Integer applytype;

    private String file1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl == null ? null : fileurl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAprovalid() {
        return aprovalid;
    }

    public void setAprovalid(Integer aprovalid) {
        this.aprovalid = aprovalid;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getKnowbrand() {
        return knowbrand;
    }

    public void setKnowbrand(String knowbrand) {
        this.knowbrand = knowbrand == null ? null : knowbrand.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Integer getWorkdate() {
        return workdate;
    }

    public void setWorkdate(Integer workdate) {
        this.workdate = workdate;
    }

    public Integer getApplytype() {
        return applytype;
    }

    public void setApplytype(Integer applytype) {
        this.applytype = applytype;
    }

    public String getUpdatedateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return updatedate == null ? "" : sdf.format(updatedate);
    }

    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }
}