package com.li.yun.biao.user.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserVo implements Serializable {
    private List<Integer> uidList;
    private List<Integer> statusList;
    private Date startCreateTime;
    private Date endCreateTime;

    public List<Integer> getUidList() {
        return uidList;
    }

    public void setUidList(List<Integer> uidList) {
        this.uidList = uidList;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
}
