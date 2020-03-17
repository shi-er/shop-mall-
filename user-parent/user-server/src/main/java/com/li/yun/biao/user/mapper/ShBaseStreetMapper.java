package com.li.yun.biao.user.mapper;

import com.li.yun.biao.user.model.ShBaseStreet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShBaseStreetMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(ShBaseStreet record);

    int insertSelective(ShBaseStreet record);

    ShBaseStreet selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(ShBaseStreet record);

    int updateByPrimaryKey(ShBaseStreet record);

    List<ShBaseStreet> getBaseStreetList(@Param("provinceCode") Integer provinceCode, @Param("cityCode") Integer cityCode, @Param("areaCode") Integer areaCode);

    ShBaseStreet getStreetByParentsAndStreetName(@Param("provinceCode") Integer provinceCode, @Param("cityCode") Integer cityCode, @Param("areaCode") Integer areaCode, @Param("streetName") String streetName);
}