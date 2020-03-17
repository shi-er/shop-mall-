package com.li.yun.biao.goods.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.goods.model.ShGoodsAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShGoodsAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShGoodsAddress record);

    int insertSelective(ShGoodsAddress record);

    ShGoodsAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShGoodsAddress record);

    int updateByPrimaryKey(ShGoodsAddress record);

    List<ShGoodsAddress> selectResultByQuery(Query<ShGoodsAddress> query);

    Integer countResultByQuery(Query<ShGoodsAddress> query);

    Integer updateGoodsAddressStatus(@Param("uid") Integer uid, @Param("nowStatus") Integer nowStatus, @Param("status") Integer status, @Param("exceptId") Integer exceptId);

    List<ShGoodsAddress> selectGoodsAddressByUid(@Param("uid") Integer uid, @Param("status") Integer status);
}