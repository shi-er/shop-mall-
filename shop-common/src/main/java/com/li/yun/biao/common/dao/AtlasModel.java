package com.li.yun.biao.common.dao;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class AtlasModel implements Serializable {
    /**
     * 经纬度
     */
    private String ingLat;
    /**
     * ip
     */
    private String ip;
    /**
     * 所属省
     */
    private String province;
    /**
     * 省code
     */
    private Integer provinceCode;
    /**
     * 所属市
     */
    private String city;
    /**
     * 市code
     */
    private Integer cityCode;
    /**
     * 所属地区
     */
    private String area;
    /**
     * 地区code
     */
    private Integer areaCode;
    /**
     * 街道乡镇
     */
    private String streetName;
    /**
     * 街道乡镇code
     */
    private Integer streetCode;
    /**
     * 地址
     */
    private String address;

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ingLat", ingLat);
        jsonObject.put("ip", ip);
        jsonObject.put("province", province);
        jsonObject.put("provinceCode", provinceCode);
        jsonObject.put("city", city);
        jsonObject.put("cityCode", cityCode);
        jsonObject.put("area", area);
        jsonObject.put("areaCode", areaCode);
        jsonObject.put("streetName", streetName);
        jsonObject.put("streetCode", streetCode);
        jsonObject.put("address", address);
        return jsonObject.toString();
    }
}
