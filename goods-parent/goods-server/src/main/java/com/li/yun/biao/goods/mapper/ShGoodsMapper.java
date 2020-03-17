package com.li.yun.biao.goods.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.goods.model.ShGoods;

import java.util.List;

public interface ShGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShGoods record);

    int insertSelective(ShGoods record);

    ShGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShGoods record);

    int updateByPrimaryKey(ShGoods record);

    List<ShGoods> selectResultByQuery(Query<ShGoods> query);

    Integer countResultByQuery(Query<ShGoods> query);
}