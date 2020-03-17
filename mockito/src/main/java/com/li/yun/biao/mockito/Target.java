package com.li.yun.biao.mockito;

/**
 * @Author: liyunbiao
 * @Date: 2019/4/22 14:34
 */
public class Target {
    private String name = "default";

    public String getName() {
        return name;
    }

    public String someMethod(String arg) {
        return arg + "!!!";
    }

    static String staticMethod() {
        return "static";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
