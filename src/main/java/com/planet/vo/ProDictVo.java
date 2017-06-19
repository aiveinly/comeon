package com.planet.vo;

/**
 * Created by junhua on 2016/3/23.
 */
public class ProDictVo {
    private Integer did;

    private Integer type;

    private String dictname;

    private String pre;

    private Integer parentid;

    private String imgurl;

    private Integer sn;

    private String bindinfo;

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDictname() {
        return dictname;
    }

    public void setDictname(String dictname) {
        this.dictname = dictname;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getBindinfo() {
        return bindinfo;
    }

    public void setBindinfo(String bindinfo) {
        this.bindinfo = bindinfo;
    }
}
