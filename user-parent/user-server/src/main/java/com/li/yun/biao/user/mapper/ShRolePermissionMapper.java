package com.li.yun.biao.user.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.user.model.ShRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShRolePermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShRolePermission record);

    int insertSelective(ShRolePermission record);

    ShRolePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShRolePermission record);

    int updateByPrimaryKey(ShRolePermission record);

    List<Integer> selectRolePermissionByRid(@Param("rid") Integer rid, @Param("pType") Integer pType);

    List<ShRolePermission> selectResultByQuery(Query<ShRolePermission> query);

    Integer countResultByQuery(Query<ShRolePermission> query);

    Integer deleteRolePermissionByPidAndRid(@Param("pid") Integer pid, @Param("rid") Integer rid, @Param("pType") Integer pType);

    ShRolePermission selectRolePermissionByPidAndRid(@Param("pid") Integer pid, @Param("rid") Integer rid, @Param("pType") Integer pType);
}