package com.li.yun.biao.goods.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.goods.model.ShGoodsCategory;

import java.util.List;

public interface ShGoodsCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShGoodsCategory record);

    int insertSelective(ShGoodsCategory record);

    ShGoodsCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShGoodsCategory record);

    int updateByPrimaryKey(ShGoodsCategory record);

    List<ShGoodsCategory> selectResultByQuery(Query<ShGoodsCategory> query);

    Integer countResultByQuery(Query<ShGoodsCategory> query);
}