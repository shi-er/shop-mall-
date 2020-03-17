package com.li.yun.biao.mockito.dao;

import com.li.yun.biao.mockito.model.Person;

/**
 * @Author: liyunbiao
 * @Date: 2019/4/22 14:44
 */
public interface PersonDao {

    Person getPerson(int id);

    boolean update(Person person);
}
