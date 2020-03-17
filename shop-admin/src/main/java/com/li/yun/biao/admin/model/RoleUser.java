package com.li.yun.biao.admin.model;

import java.io.Serializable;

public class RoleUser implements Serializable {
    private Integer id;
    private Integer rid;
    private Integer uid;
    private String mobile;
    private String name;
    private String roleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "RoleUser{" +
                "id=" + id +
                ", rid=" + rid +
                ", uid='" + uid + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
