package com.li.yun.biao.user.model;

import lombok.Data;

@Data
public class ShBaseVillage {
    private Long code;

    private String name;

    private Integer streetCode;

    private Integer areaCode;

    private Integer cityCode;

    private Integer provinceCode;
}