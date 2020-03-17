package com.li.yun.biao.user.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.user.model.ShUserLoginRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShUserLoginRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShUserLoginRecord record);

    int insertSelective(ShUserLoginRecord record);

    ShUserLoginRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShUserLoginRecord record);

    int updateByPrimaryKey(ShUserLoginRecord record);

    ShUserLoginRecord selectUserLoginRecord(@Param("uid") Integer uid, @Param("isLast") Integer isLast);

    List<ShUserLoginRecord> selectResultByQuery(Query<ShUserLoginRecord> query);

    Integer countResultByQuery(Query<ShUserLoginRecord> query);
}