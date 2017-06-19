package com.planet.proproductsale.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProProductSale {
    private String psid;

    private String pid;

    private Integer stock;

    private Integer ptype;

    private BigDecimal price;

    private String remark;

    private Date saledate;

    private Integer status;

    private String stay1;

    private String stay2;

    private Integer sn;

    public String getPsid() {
        return psid;
    }

    public void setPsid(String psid) {
        this.psid = psid == null ? null : psid.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getSaledate() {
        return saledate;
    }

    public void setSaledate(Date saledate) {
        this.saledate = saledate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStay1() {
        return stay1;
    }

    public void setStay1(String stay1) {
        this.stay1 = stay1 == null ? null : stay1.trim();
    }

    public String getStay2() {
        return stay2;
    }

    public void setStay2(String stay2) {
        this.stay2 = stay2 == null ? null : stay2.trim();
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getSaledateString(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (saledate != null) {
            return df.format(saledate);
        } else {
            return null;
        }
    }
}