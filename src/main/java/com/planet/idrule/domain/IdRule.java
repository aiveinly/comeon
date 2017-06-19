package com.planet.idrule.domain;

public class IdRule {
    private Integer id;

    private String numName;

    private Integer numCount;

    private Integer curNum;

    private String lastNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumName() {
        return numName;
    }

    public void setNumName(String numName) {
        this.numName = numName == null ? null : numName.trim();
    }

    public Integer getNumCount() {
        return numCount;
    }

    public void setNumCount(Integer numCount) {
        this.numCount = numCount;
    }

    public Integer getCurNum() {
        return curNum;
    }

    public void setCurNum(Integer curNum) {
        this.curNum = curNum;
    }

    public String getLastNum() {
        return lastNum;
    }

    public void setLastNum(String lastNum) {
        this.lastNum = lastNum == null ? null : lastNum.trim();
    }
}