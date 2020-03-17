package com.li.yun.biao.mockito.model;

/**
 * @Author: liyunbiao
 * @Date: 2019/4/22 14:43
 */
public class Person {

    private final int id;
    private final String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
