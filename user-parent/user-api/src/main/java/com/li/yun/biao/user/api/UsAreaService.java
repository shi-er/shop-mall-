package com.li.yun.biao.user.api;

import com.li.yun.biao.common.dao.AtlasModel;
import com.li.yun.biao.user.model.*;

import java.util.List;

/**
 * 地区接口
 */
public interface UsAreaService {

    /**
     * 省份
     */
    Integer addBaseProvince(ShBaseProvince baseProvince);
    Integer updateBaseProvince(ShBaseProvince baseProvince);
    ShBaseProvince getBaseProvinceByCode(Integer code);
    ShBaseProvince getBaseProvinceByName(String name);
    List<ShBaseProvince> getBaseProvinceList();

    /**
     * 城市
     */
    Integer addBaseCity(ShBaseCity baseCity);
    Integer updateBaseCity(ShBaseCity baseCity);
    ShBaseCity getBaseCityById(Integer code);
    ShBaseCity getCityByProvinceCodeAndCityName(Integer provinceCode,String cityName);
    List<ShBaseCity> getBaseCityList(Integer provinceCode);

    /**
     * 地区
     */
    Integer addBaseArea(ShBaseArea baseArea);
    Integer updateBaseArea(ShBaseArea baseArea);
    ShBaseArea getBaseAreaByCode(Integer code);
    ShBaseArea getAreaByAreaName(Integer provinceCode, Integer cityCode,String areaName);
    List<ShBaseArea> getBaseAreaList(Integer provinceCode,Integer cityCode);


    /**
     * 乡镇街道
     */
    Integer addBaseStreet(ShBaseStreet baseStreet);
    Integer updateBaseStreet(ShBaseStreet baseStreet);
    ShBaseStreet getBaseStreetByCode(Integer code);
    ShBaseStreet getStreetByParentsAndStreetName(Integer provinceCode, Integer cityCode, Integer areaCode,String StreetName);
    List<ShBaseStreet> getBaseStreetList(Integer provinceCode,Integer cityCode,Integer areaCode);

    /**
     * 村委
     */
    Integer addBaseVillage(ShBaseVillage baseVillage);
    Integer updateBaseVillage(ShBaseVillage baseVillage);
    ShBaseVillage getBaseVillageByCode(Long code);
    List<ShBaseVillage> getBaseVillageList(Integer provinceCode,Integer cityCode,Integer areaCode,Integer streetCode);

    /**
     * 获取地址信息
     * @param lngLat 经纬度
     * @param ip ip
     */
    AtlasModel getAtlasModelByLngLatOrIp(String lngLat,String ip);
}
