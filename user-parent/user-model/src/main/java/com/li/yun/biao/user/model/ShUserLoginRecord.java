package com.li.yun.biao.user.model;

import com.li.yun.biao.user.vo.UserVo;

import java.io.Serializable;
import java.util.Date;

public class ShUserLoginRecord extends UserVo implements Serializable {
    private Integer id;

    private Integer uid;

    private String realname;

    private Date loginTime;

    private String loginAdress;

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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginAdress() {
        return loginAdress;
    }

    public void setLoginAdress(String loginAdress) {
        this.loginAdress = loginAdress == null ? null : loginAdress.trim();
    }
}