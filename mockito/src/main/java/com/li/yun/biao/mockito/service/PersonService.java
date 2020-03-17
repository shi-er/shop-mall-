package com.li.yun.biao.mockito.service;

import com.li.yun.biao.mockito.dao.PersonDao;
import com.li.yun.biao.mockito.model.Person;

/**
 * @Author: liyunbiao
 * @Date: 2019/4/22 14:45
 */
public class PersonService {

    private final PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public boolean update(int id, String name) {
        Person person = personDao.getPerson(id);
        if (person == null) {
            return false;
        }

        Person personUpdate = new Person(person.getId(), name);
        return personDao.update(personUpdate);
    }
}