package com.planet.user.controller;

/**
 * Created by yjj on 2016/3/2.
 */
public class QuoPriceVo {
    private String poid;
    private String price;
    private String qty;
    private String quoPrice;

    public String getQuoPrice() {
        return quoPrice;
    }

    public void setQuoPrice(String quo_price) {
        this.quoPrice = quo_price;
    }

    public String getPoid() {
        return poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
