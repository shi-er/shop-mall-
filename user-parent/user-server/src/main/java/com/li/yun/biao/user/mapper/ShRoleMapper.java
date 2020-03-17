package com.li.yun.biao.user.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.user.model.ShRole;

import java.util.List;

public interface ShRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShRole record);

    int insertSelective(ShRole record);

    ShRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShRole record);

    int updateByPrimaryKey(ShRole record);

    List<ShRole> selectResultByQuery(Query<ShRole> query);

    Integer countResultByQuery(Query<ShRole> query);
}