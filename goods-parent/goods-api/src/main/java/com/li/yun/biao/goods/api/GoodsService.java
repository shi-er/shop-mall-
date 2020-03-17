package com.li.yun.biao.goods.api;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.goods.model.*;

import java.util.List;

public interface GoodsService {
    Boolean automaticAddGoods();
    Boolean addGoods(ShGoods goods);
    Boolean updateGoods(ShGoods goods);
    ShGoods selectGoodsById(Integer id);
    Result<ShGoods> getGoodsResultByQuery(Query<ShGoods> query);

    Boolean automaticAddGoodsCategory();
    Boolean addGoodsCategory(ShGoodsCategory goodsCategory);
    Boolean updateGoodsCategory(ShGoodsCategory goodsCategory);
    ShGoodsCategory selectGoodsCategoryById(Integer id);
    Result<ShGoodsCategory> getGoodsCategoryResultByQuery(Query<ShGoodsCategory> query);

    Boolean automaticAddGoodsDetail();
    Boolean addGoodsDetail(ShGoodsDetail goodsDetail);
    Boolean updateGoodsDetail(ShGoodsDetail goodsDetail);
    ShGoodsDetail selectGoodsDetailById(Integer id);
    List<ShGoodsDetail> getGoodsDetailList(Integer goodsId);
    Result<ShGoodsDetail> getGoodsDetailResultByQuery(Query<ShGoodsDetail> query);

    Boolean automaticAddGoodsPhotoWall();
    Boolean addGoodsPhotoWall(ShGoodsPhotoWall goodsPhotoWall);
    Boolean updateGoodsPhotoWall(ShGoodsPhotoWall goodsPhotoWall);
    ShGoodsPhotoWall selectGoodsPhotoWallById(Integer id);
    Result<ShGoodsPhotoWall> getGoodsPhotoWallResultByQuery(Query<ShGoodsPhotoWall> query);

    Boolean automaticAddGoodsAddress();
    Boolean addGoodsAddress(ShGoodsAddress goodsAddress);
    Boolean updateGoodsAddress(ShGoodsAddress goodsAddress);
    Boolean updateGoodsAddressStatus(Integer uid,Integer nowStatus,Integer status,Integer exceptId);
    ShGoodsAddress selectGoodsAddressById(Integer id);
    List<ShGoodsAddress> selectGoodsAddressByUid(Integer uid,Integer status);
    Result<ShGoodsAddress> getGoodsAddressResultByQuery(Query<ShGoodsAddress> query);

}
