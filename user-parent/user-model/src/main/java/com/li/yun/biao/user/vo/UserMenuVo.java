package com.li.yun.biao.user.vo;

import java.io.Serializable;
import java.util.List;

public class UserMenuVo implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 地址
     */
    private String url;

    /**
     * 权限编码
     */
    private String permCode;

    /**
     * 图标
     */
    private String icon;

    private List<UserMenuVo> mvList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermCode() {
        return permCode;
    }

    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<UserMenuVo> getMvList() {
        return mvList;
    }

    public void setMvList(List<UserMenuVo> mvList) {
        this.mvList = mvList;
    }
}
