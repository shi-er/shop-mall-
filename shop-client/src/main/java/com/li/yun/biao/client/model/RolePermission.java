package com.li.yun.biao.client.model;

import com.li.yun.biao.user.model.ShPermission;

import java.io.Serializable;

public class RolePermission implements Serializable {
    private Integer id;
    private Integer pid;
    private String title;
    private Short type;
    private Short state;
    private String permcode;
    private String description;
    private Integer status;
    private String parentName;

    public RolePermission() {
    }

    public RolePermission(ShPermission permission) {
        this.id = permission.getId();
        this.pid = permission.getPid();
        this.title = permission.getTitle();
        this.type = permission.getType();
        this.state = permission.getState();
        this.permcode = permission.getPermcode();
        this.description = permission.getDescription();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getPermcode() {
        return permcode;
    }

    public void setPermcode(String permcode) {
        this.permcode = permcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
