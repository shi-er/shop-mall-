package com.li.yun.biao.user.service;

import com.li.yun.biao.common.dao.AtlasModel;
import com.li.yun.biao.common.utils.MapToolsUtil;
import com.li.yun.biao.common.utils.StringUtil;
import com.li.yun.biao.user.api.UsAreaService;
import com.li.yun.biao.user.mapper.*;
import com.li.yun.biao.user.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("usAreaService")
public class UsAreaServiceImpl implements UsAreaService {
    @Resource
    private ShBaseProvinceMapper shBaseProvinceMapper;
    @Resource
    private ShBaseCityMapper shBaseCityMapper;
    @Resource
    private ShBaseAreaMapper shBaseAreaMapper;
    @Resource
    private ShBaseStreetMapper shBaseStreetMapper;
    @Resource
    private ShBaseVillageMapper shBaseVillageMapper;


    /**
     * 省份
     */
    @Override
    public Integer addBaseProvince(ShBaseProvince baseProvince) {
        return shBaseProvinceMapper.insertSelective(baseProvince);
    }

    @Override
    public Integer updateBaseProvince(ShBaseProvince baseProvince) {
        return shBaseProvinceMapper.updateByPrimaryKeySelective(baseProvince);
    }

    @Override
    public ShBaseProvince getBaseProvinceByCode(Integer code) {
        return shBaseProvinceMapper.selectByPrimaryKey(code);
    }

    @Override
    public ShBaseProvince getBaseProvinceByName(String name) {
        return shBaseProvinceMapper.getBaseProvinceByName(name);
    }

    @Override
    public List<ShBaseProvince> getBaseProvinceList() {
        return shBaseProvinceMapper.getBaseProvinceList();
    }

    /**
     * 城市
     */
    @Override
    public Integer addBaseCity(ShBaseCity baseCity) {
        return shBaseCityMapper.insertSelective(baseCity);
    }

    @Override
    public Integer updateBaseCity(ShBaseCity baseCity) {
        return shBaseCityMapper.updateByPrimaryKeySelective(baseCity);
    }

    @Override
    public ShBaseCity getBaseCityById(Integer code) {
        return shBaseCityMapper.selectByPrimaryKey(code);
    }

    @Override
    public ShBaseCity getCityByProvinceCodeAndCityName(Integer provinceCode, String cityName) {
        return shBaseCityMapper.getCityByProvinceCodeAndCityName(provinceCode, cityName);
    }

    @Override
    public List<ShBaseCity> getBaseCityList(Integer provinceCode) {
        return shBaseCityMapper.getBaseCityList(provinceCode);
    }

    /**
     * 地区
     */
    @Override
    public Integer addBaseArea(ShBaseArea baseArea) {
        return shBaseAreaMapper.insertSelective(baseArea);
    }

    @Override
    public Integer updateBaseArea(ShBaseArea baseArea) {
        return shBaseAreaMapper.updateByPrimaryKeySelective(baseArea);
    }

    @Override
    public ShBaseArea getBaseAreaByCode(Integer code) {
        return shBaseAreaMapper.selectByPrimaryKey(code);
    }

    @Override
    public ShBaseArea getAreaByAreaName(Integer provinceCode, Integer cityCode, String areaName) {
        return shBaseAreaMapper.getAreaByAreaName(provinceCode, cityCode, areaName);
    }

    @Override
    public List<ShBaseArea> getBaseAreaList(Integer provinceCode, Integer cityCode) {
        return shBaseAreaMapper.getBaseAreaList(provinceCode, cityCode);
    }

    /**
     * 乡镇街道
     */
    @Override
    public Integer addBaseStreet(ShBaseStreet baseStreet) {
        return shBaseStreetMapper.insertSelective(baseStreet);
    }

    @Override
    public Integer updateBaseStreet(ShBaseStreet baseStreet) {
        return shBaseStreetMapper.updateByPrimaryKeySelective(baseStreet);
    }

    @Override
    public ShBaseStreet getBaseStreetByCode(Integer code) {
        return shBaseStreetMapper.selectByPrimaryKey(code);
    }

    @Override
    public ShBaseStreet getStreetByParentsAndStreetName(Integer provinceCode, Integer cityCode, Integer areaCode, String StreetName) {
        return shBaseStreetMapper.getStreetByParentsAndStreetName(provinceCode, cityCode, areaCode, StreetName);
    }

    @Override
    public List<ShBaseStreet> getBaseStreetList(Integer provinceCode, Integer cityCode, Integer areaCode) {
        if (Objects.isNull(provinceCode) && Objects.isNull(cityCode) && Objects.isNull(areaCode)) {
            return new ArrayList<>();
        }
        return shBaseStreetMapper.getBaseStreetList(provinceCode, cityCode, areaCode);
    }

    /**
     * 村委
     */
    @Override
    public Integer addBaseVillage(ShBaseVillage baseVillage) {
        return shBaseVillageMapper.insertSelective(baseVillage);
    }

    @Override
    public Integer updateBaseVillage(ShBaseVillage baseVillage) {
        return shBaseVillageMapper.updateByPrimaryKeySelective(baseVillage);
    }

    @Override
    public ShBaseVillage getBaseVillageByCode(Long code) {
        return shBaseVillageMapper.selectByPrimaryKey(code);
    }

    @Override
    public List<ShBaseVillage> getBaseVillageList(Integer provinceCode, Integer cityCode, Integer areaCode, Integer streetCode) {
        if (Objects.isNull(provinceCode) && Objects.isNull(cityCode) && Objects.isNull(areaCode) && Objects.isNull(streetCode)) {
            return new ArrayList<>();
        }
        return shBaseVillageMapper.getBaseVillageList(provinceCode, cityCode, areaCode, streetCode);
    }

    /**
     * 获取地址信息
     *
     * @param lngLat 经纬度
     * @param ip     ip
     */
    @Override
    public AtlasModel getAtlasModelByLngLatOrIp(String lngLat, String ip) {
        AtlasModel atlasModel = MapToolsUtil.getAddressBylngLatOrIp(lngLat, ip);
        if (Objects.isNull(atlasModel)) return null;
        String provinceName = atlasModel.getProvince();
        String cityName = atlasModel.getCity();
        String areaName = atlasModel.getArea();
        String streetName = atlasModel.getStreetName();
        if (StringUtil.isBlank(provinceName) && StringUtil.isBlank(cityName) && StringUtil.isBlank(areaName)) {
            return null;
        }
        Integer provinceCode = null, cityCode = null, areaCode = null;
        if (StringUtil.isNotBlank(provinceName)) {
            //获取省份
            ShBaseProvince baseProvince = getBaseProvinceByName(provinceName);
            if (Objects.nonNull(baseProvince)) {
                provinceCode = baseProvince.getCode();
                atlasModel.setProvinceCode(provinceCode);
            }
        }
        if (StringUtil.isNotBlank(cityName)) {
            //获取城市
            ShBaseCity baseCity = getCityByProvinceCodeAndCityName(provinceCode, cityName);
            if (Objects.nonNull(baseCity)) {
                cityCode = baseCity.getCode();
                atlasModel.setCityCode(cityCode);
                if (Objects.isNull(provinceCode) && Objects.nonNull(baseCity.getProvinceCode())) {
                    atlasModel.setProvinceCode(baseCity.getProvinceCode());
                    provinceCode = baseCity.getProvinceCode();
                }
            }
        }
        if (StringUtil.isNotBlank(areaName)) {
            //获取区域
            ShBaseArea baseArea = getAreaByAreaName(provinceCode, cityCode, areaName);
            if (Objects.nonNull(baseArea) && Objects.nonNull(baseArea.getCode())) {
                areaCode = baseArea.getCode();
                atlasModel.setAreaCode(areaCode);
                if (Objects.isNull(provinceCode) && Objects.nonNull(baseArea.getProvinceCode())) {
                    atlasModel.setProvinceCode(baseArea.getProvinceCode());
                    provinceCode = baseArea.getProvinceCode();
                }
                if (Objects.isNull(cityCode) && Objects.nonNull(baseArea.getCityCode())) {
                    atlasModel.setCityCode(baseArea.getCityCode());
                    cityCode = baseArea.getCityCode();
                }
            }
        }
        if (Objects.isNull(provinceCode) && Objects.isNull(cityCode) && Objects.isNull(areaCode)) {
            return null;
        }
        if (Objects.nonNull(streetName)) {
            //获取乡镇街道
            ShBaseStreet baseStreet = getStreetByParentsAndStreetName(provinceCode, cityCode, areaCode, streetName);
            if (Objects.nonNull(baseStreet)) {
                atlasModel.setStreetCode(baseStreet.getCode());
            }
        }
        return atlasModel;
    }

}
