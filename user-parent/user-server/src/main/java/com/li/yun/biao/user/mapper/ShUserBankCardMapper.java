package com.li.yun.biao.user.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.user.model.ShUserBankCard;

import java.util.List;

public interface ShUserBankCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShUserBankCard record);

    int insertSelective(ShUserBankCard record);

    ShUserBankCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShUserBankCard record);

    int updateByPrimaryKey(ShUserBankCard record);

    List<ShUserBankCard> selectUserBankCardUid(Integer uid);

    List<ShUserBankCard> selectResultByQuery(Query<ShUserBankCard> query);

    Integer countResultByQuery(Query<ShUserBankCard> query);
}