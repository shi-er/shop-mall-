package com.li.yun.biao.user.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.user.model.ShUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShUserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShUserRole record);

    int insertSelective(ShUserRole record);

    ShUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShUserRole record);

    int updateByPrimaryKey(ShUserRole record);

    ShUserRole selectUserRoleByUid(@Param("uid") Integer uid, @Param("pType") Integer pType);

    List<ShUserRole> selectResultByQuery(Query<ShUserRole> query);

    Integer countResultByQuery(Query<ShUserRole> query);
}