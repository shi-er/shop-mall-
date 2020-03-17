package com.li.yun.biao.user.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.user.model.ShPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShPermission record);

    int insertSelective(ShPermission record);

    ShPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShPermission record);

    int updateByPrimaryKey(ShPermission record);

    List<ShPermission> selectPermissionListByPid(@Param("pid") Integer pid, @Param("pType") Integer pType);

    List<ShPermission> selectPermissionListByIdList(@Param("idList") List<Integer> idList, @Param("pType") Integer pType);

    List<ShPermission> selectResultByQuery(Query<ShPermission> query);

    Integer countResultByQuery(Query<ShPermission> query);

    List<String> selectPermCodeById(@Param("permissionIdList") List<Integer> permissionIdList);

    ShPermission selectPermissionByCode(@Param("permCode") String permCode, @Param("pType") Integer pType);

    ShPermission selectPermissionBySort(Integer sort);

    void updateSort();
}