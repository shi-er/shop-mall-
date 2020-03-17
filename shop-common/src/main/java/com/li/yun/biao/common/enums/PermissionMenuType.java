package com.li.yun.biao.common.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * 权限菜单类型
 */
public enum PermissionMenuType {


    JAVA_WEB(0, "java_web"),
    VUE(1, "vue");

    public final Integer type;
    public final String name;

    PermissionMenuType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getTypeName(Integer type) {
        return Arrays.stream(values()).anyMatch(item -> Objects.equals(type, item.type)) ? Arrays.stream(values()).filter(item -> Objects.equals(type, item.type)).findFirst().get().name : null;
    }
}
