package com.li.yun.biao.user.mapper;

import com.li.yun.biao.user.model.ShBaseProvince;

import java.util.List;

public interface ShBaseProvinceMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(ShBaseProvince record);

    int insertSelective(ShBaseProvince record);

    ShBaseProvince selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(ShBaseProvince record);

    int updateByPrimaryKey(ShBaseProvince record);

    List<ShBaseProvince> getBaseProvinceList();

    ShBaseProvince getBaseProvinceByName(String name);
}