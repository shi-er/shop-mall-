package com.li.yun.biao.user.model;

import java.io.Serializable;

public class ShPermission implements Serializable {
    private Integer id;

    private Integer pid;

    private String title;

    private Short type;

    private Short state;

    private Integer sort;

    private String url;

    private String permcode;

    private String icon;

    private String description;

    private Integer pType;

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
        this.title = title == null ? null : title.trim();
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPermcode() {
        return permcode;
    }

    public void setPermcode(String permcode) {
        this.permcode = permcode == null ? null : permcode.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    @Override
    public String toString() {
        return "ShPermission{" +
                "id=" + id +
                ", pid=" + pid +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", state=" + state +
                ", sort=" + sort +
                ", url='" + url + '\'' +
                ", permcode='" + permcode + '\'' +
                ", icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", pType=" + pType +
                '}';
    }
}