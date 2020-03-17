package com.li.yun.biao.user.api;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.user.model.ShPermission;
import com.li.yun.biao.user.model.ShRole;
import com.li.yun.biao.user.model.ShRolePermission;
import com.li.yun.biao.user.model.ShUserRole;
import com.li.yun.biao.user.vo.UserMenuVo;

import java.util.List;

/**
 * 权限接口
 */
public interface UsPermissionService {
    /**
     * 角色管理
     */
    Integer addRole(ShRole role);
    Integer updateRole(ShRole role);
    Integer deleteRole(Integer id);
    ShRole getRoleById(Integer id);
    Result<ShRole> getRoleResult(Query<ShRole> query);

    /**
     * 权限管理
     */
    Integer addPermission(ShPermission permission);
    Integer updatePermission(ShPermission permission);
    Integer deletePermission(Integer id);
    ShPermission getPermissionById(Integer id);
    ShPermission getPermissionByCode(String permCode,Integer pType);
    ShPermission getPermissionBySort(Integer sort);
    List<String> getPermCode(Integer uid,Integer pType);
    List<ShPermission> getPermissionListByPid(Integer pid,Integer pType);
    List<ShPermission> getPermissionListByIdList(List<Integer> idList,Integer pType);
    Result<ShPermission> getPermissionResult(Query<ShPermission> query);


    /**
     * 角色权限
     */

    Integer addRolePermission(ShRolePermission rolePermission);
    Integer updateRolePermission(ShRolePermission rolePermission);
    Integer deleteRolePermission(Integer id);
    Integer deleteRolePermissionByPidAndRid(Integer pid,Integer rid,Integer pType);
    ShRolePermission getRolePermissionById(Integer id);
    ShRolePermission getRolePermissionByPidAndRid(Integer pid,Integer rid,Integer pType);
    List<Integer> getRolePermissionByRid(Integer rid,Integer pType);
    Result<ShRolePermission> getRolePermissionResult(Query<ShRolePermission> query);

    /**
     * 用户角色
     * @param userRole
     * @return
     */
    Integer addUserRole(ShUserRole userRole);
    Integer updateUserRole(ShUserRole userRole);
    Integer deleteUserRole(Integer id);
    ShUserRole getUserRoleById(Integer id);
    ShUserRole getUserRoleByUid(Integer uid,Integer pType);
    List<UserMenuVo> getUserMenuVoByUid(Integer uid,Integer pType);
    Result<ShUserRole> getUserRoleResult(Query<ShUserRole> query);
}
