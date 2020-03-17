package com.li.yun.biao.user.mapper;

import com.li.yun.biao.user.model.ShBaseCity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShBaseCityMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(ShBaseCity record);

    int insertSelective(ShBaseCity record);

    ShBaseCity selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(ShBaseCity record);

    int updateByPrimaryKey(ShBaseCity record);

    List<ShBaseCity> getBaseCityList(@Param("provinceCode") Integer provinceCode);

    ShBaseCity getCityByProvinceCodeAndCityName(@Param("provinceCode") Integer provinceCode, @Param("cityName") String cityName);
}