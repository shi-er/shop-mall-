package com.li.yun.biao.user.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.user.model.ShBankCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShBankCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShBankCard record);

    int insertSelective(ShBankCard record);

    ShBankCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShBankCard record);

    int updateByPrimaryKey(ShBankCard record);

    ShBankCard selectBankCardByBin(String bin);

    ShBankCard selectByBinAndLen(@Param("bin") String BIN, @Param("cardNoLength") Integer cardNoLength);

    List<ShBankCard> getResultByQuery(Query<ShBankCard> query);

    Integer countByQuery(Query<ShBankCard> query);
}