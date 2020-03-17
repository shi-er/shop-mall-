package com.li.yun.biao.common.dao;

import java.io.Serializable;

public class Token implements Serializable {
    private String uid;
    private String ip;
    private long time = System.currentTimeMillis();
    private int flag = 0;

    public Token() {
    }

    public Token(String uid, String ip) {
        this.uid = uid;
        this.ip = ip;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
