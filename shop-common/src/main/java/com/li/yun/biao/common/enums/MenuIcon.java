package com.li.yun.biao.common.enums;

import com.li.yun.biao.common.utils.StringUtil;

import java.util.Arrays;
import java.util.Objects;

public enum MenuIcon {
    Dashboards("fa fa-th-large", "仪表板"),
    Layouts("fa fa-diamond", "布局"),
    Graphs("fa fa-bar-chart-o", "图"),
    Mailbox("fa fa-envelope", "信箱"),
    Metrics("fa fa-pie-chart", "韵律学"),
    Widgets("fa fa-flask", "小部件"),
    Forms("fa fa-edit", "形式"),
    AppViews("fa fa-desktop", "应用程序"),
    OtherPages("fa fa-files-o", "页面"),
    Miscellaneous("fa fa-globe", "其他"),
    UIElements("fa fa-flask", "界面元素"),
    GridOptions("fa fa-laptop", "网格选项"),
    Tables("fa fa-table", "桌子"),
    ECommerce("fa fa-shopping-cart", "商业"),
    Gallery("fa fa-picture-o", "画廊"),
    Sitemap("fa fa-sitemap", "站点地图"),
    Database("fa fa-database", "数据库"),
    Star("fa fa-star", "星星"),
    Magic("fa fa-magic", "魔术");
    private final String icon;
    private final String iconName;

    MenuIcon(String icon, String iconName) {
        this.icon = icon;
        this.iconName = iconName;
    }

    public String getIcon() {
        return icon;
    }

    public String getIconName() {
        return iconName;
    }

    public static String getIcon(String iconName) {
        if (StringUtil.isBlank(iconName)) return "";
        return Arrays.stream(values()).anyMatch(item -> Objects.equals(iconName, item.iconName)) ? Arrays.stream(values()).filter(item -> Objects.equals(iconName, item.iconName)).findFirst().get().icon : "";
    }
}
