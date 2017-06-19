package com.planet.vo;

import java.math.BigDecimal;

/**
 * Created by Li on 2016/2/1.
 */
public class ProductListBgVo {

    /**
     * 产品id
     */
    private String pid;

    /**
     * 产品名称
     */
    private String productname;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 产品类型 1 特价产品 2 推荐产品 3 普通产品
     */
    private Integer ptype;

    /**
     * 品类ID
     */
    private Integer sortid;

    /**
     * 品类名称
     */
    private String sortname;

    /**
     * 品牌id
     */
    private Integer brandid;

    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 系列id
     */
    private Integer seriesid;

    /**
     * 系列名称
     */
    private String seriesname;

    /**
     * 图片地址
     */
    private String imgurl;
    /**
     * 图片地址2
     */
    private String imgurl2;
    /**
     * 型号名
     */
    private String modelname;

    /**
     * 描述
     */
    private String describemodel;

    /**
     * 是否发布
     */
    private String isIssue;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Integer getSortid() {
        return sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public Integer getBrandid() {
        return brandid;
    }

    public void setBrandid(Integer brandid) {
        this.brandid = brandid;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public Integer getSeriesid() {
        return seriesid;
    }

    public void setSeriesid(Integer seriesid) {
        this.seriesid = seriesid;
    }

    public String getSeriesname() {
        return seriesname;
    }

    public void setSeriesname(String seriesname) {
        this.seriesname = seriesname;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
    public String getImgurl2() {
        return imgurl2;
    }

    public void setImgurl2(String imgurl2) {
        this.imgurl2 = imgurl2;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getDescribemodel() {
        return describemodel;
    }

    public void setDescribemodel(String describemodel) {
        this.describemodel = describemodel;
    }


    public String getIsIssue() {
        return isIssue;
    }

    public void setIsIssue(String isIssue) {
        this.isIssue = isIssue;
    }
}
