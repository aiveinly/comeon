package com.planet.ordpreorder.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrdPreOrder {
    private String poid;

    private String rid;

    private Integer uid;

    private String oid;

    private String qid;

    private String productname;

    private BigDecimal price;

    private BigDecimal quoPrice;

    private Integer qty;

    private String remark;

    private Date updatedate;

    private Integer updateid;

    private Integer status;

    private String sortname;

    private String brandname;

    private String modelname;

    private String seriesname;

    private String deliverytime;

    private Integer stock;

    private Integer ptype;

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Integer getStock() {

        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDeliverytime() {

        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getPoid() {
        return poid;
    }

    public void setPoid(String poid) {
        this.poid = poid == null ? null : poid.trim();
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid == null ? null : rid.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid == null ? null : qid.trim();
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuoPrice() {
        return quoPrice;
    }

    public void setQuoPrice(BigDecimal quoPrice) {
        this.quoPrice = quoPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Integer getUpdateid() {return updateid; }

    public void setUpdateid(Integer updateid) {this.updateid = updateid;}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname == null ? null : sortname.trim();
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname == null ? null : brandname.trim();
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname == null ? null : modelname.trim();
    }

    public String getSeriesname() {
        return seriesname;
    }

    public void setSeriesname(String seriesname) {
        this.seriesname = seriesname == null ? null : seriesname.trim();
    }


    public String getUpDateDateString(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (updatedate != null) {
            return df.format(updatedate);
        } else {
            return null;
        }
    }
}