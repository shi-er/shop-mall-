package com.li.yun.biao.user.mapper;

import com.li.yun.biao.user.model.ShBaseVillage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShBaseVillageMapper {
    int deleteByPrimaryKey(Long code);

    int insert(ShBaseVillage record);

    int insertSelective(ShBaseVillage record);

    ShBaseVillage selectByPrimaryKey(Long code);

    int updateByPrimaryKeySelective(ShBaseVillage record);

    int updateByPrimaryKey(ShBaseVillage record);

    List<ShBaseVillage> getBaseVillageList(@Param("provinceCode") Integer provinceCode, @Param("cityCode") Integer cityCode, @Param("areaCode") Integer areaCode, @Param("streetCode") Integer streetCode);
}