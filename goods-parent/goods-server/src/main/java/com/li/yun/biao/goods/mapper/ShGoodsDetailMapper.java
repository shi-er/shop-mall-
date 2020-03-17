package com.li.yun.biao.goods.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.goods.model.ShGoodsDetail;

import java.util.List;

public interface ShGoodsDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShGoodsDetail record);

    int insertSelective(ShGoodsDetail record);

    ShGoodsDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShGoodsDetail record);

    int updateByPrimaryKeyWithBLOBs(ShGoodsDetail record);

    int updateByPrimaryKey(ShGoodsDetail record);

    List<ShGoodsDetail> selectGoodsDetailList(Integer goodsId);

    List<ShGoodsDetail> selectResultByQuery(Query<ShGoodsDetail> query);

    Integer countResultByQuery(Query<ShGoodsDetail> query);
}