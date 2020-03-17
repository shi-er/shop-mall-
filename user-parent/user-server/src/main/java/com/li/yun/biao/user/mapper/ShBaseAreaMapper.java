package com.li.yun.biao.user.mapper;

import com.li.yun.biao.user.model.ShBaseArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShBaseAreaMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(ShBaseArea record);

    int insertSelective(ShBaseArea record);

    ShBaseArea selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(ShBaseArea record);

    int updateByPrimaryKey(ShBaseArea record);

    List<ShBaseArea> getBaseAreaList(@Param("provinceCode") Integer provinceCode, @Param("cityCode") Integer cityCode);

    ShBaseArea getAreaByAreaName(@Param("provinceCode") Integer provinceCode, @Param("cityCode") Integer cityCode, @Param("areaName") String areaName);
}