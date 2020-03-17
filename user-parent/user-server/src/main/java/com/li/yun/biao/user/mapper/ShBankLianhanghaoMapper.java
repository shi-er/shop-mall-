package com.li.yun.biao.user.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.user.model.ShBankLianhanghao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShBankLianhanghaoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShBankLianhanghao record);

    int insertSelective(ShBankLianhanghao record);

    ShBankLianhanghao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShBankLianhanghao record);

    int updateByPrimaryKey(ShBankLianhanghao record);

    List<ShBankLianhanghao> select(@Param("bankType") String bankType, @Param("bankRegion") String bankRegion, @Param("bankName") String bankName);

    List<ShBankLianhanghao> selectListByLianhanghao(ShBankLianhanghao lianhanghao);

    ShBankLianhanghao selectByBankNumber(String bankNumber);

    ShBankLianhanghao selectByBankName(String bankName);

    List<ShBankLianhanghao> getResultByQuery(Query<ShBankLianhanghao> query);

    Integer countByQuery(Query<ShBankLianhanghao> query);
}