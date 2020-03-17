package com.li.yun.biao.goods.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.goods.model.ShGoodsPhotoWall;

import java.util.List;

public interface ShGoodsPhotoWallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShGoodsPhotoWall record);

    int insertSelective(ShGoodsPhotoWall record);

    ShGoodsPhotoWall selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShGoodsPhotoWall record);

    int updateByPrimaryKey(ShGoodsPhotoWall record);

    List<ShGoodsPhotoWall> selectResultByQuery(Query<ShGoodsPhotoWall> query);

    Integer countResultByQuery(Query<ShGoodsPhotoWall> query);
}