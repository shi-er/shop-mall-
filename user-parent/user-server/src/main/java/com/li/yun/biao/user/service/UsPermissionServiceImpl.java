package com.li.yun.biao.user.service;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.common.enums.PermissionMenuType;
import com.li.yun.biao.user.api.UsPermissionService;
import com.li.yun.biao.user.mapper.ShPermissionMapper;
import com.li.yun.biao.user.mapper.ShRoleMapper;
import com.li.yun.biao.user.mapper.ShRolePermissionMapper;
import com.li.yun.biao.user.mapper.ShUserRoleMapper;
import com.li.yun.biao.user.model.ShPermission;
import com.li.yun.biao.user.model.ShRole;
import com.li.yun.biao.user.model.ShRolePermission;
import com.li.yun.biao.user.model.ShUserRole;
import com.li.yun.biao.user.vo.UserMenuVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("usPermissionService")
public class UsPermissionServiceImpl implements UsPermissionService {
    @Resource
    private ShRolePermissionMapper shRolePermissionMapper;
    @Resource
    private ShPermissionMapper shPermissionMapper;
    @Resource
    private ShUserRoleMapper shUserRoleMapper;
    @Resource
    private ShRoleMapper shRoleMapper;


    @Override
    public Integer addRole(ShRole role) {
        return shRoleMapper.insertSelective(role);
    }

    @Override
    public Integer updateRole(ShRole role) {
        return shRoleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public Integer deleteRole(Integer id) {
        return shRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ShRole getRoleById(Integer id) {
        return shRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Result<ShRole> getRoleResult(Query<ShRole> query) {
        List<ShRole> list = shRoleMapper.selectResultByQuery(query);
        Integer total = shRoleMapper.countResultByQuery(query);
        return new Result<>(true, total, list, null, null);
    }

    @Override
    public Integer addPermission(ShPermission permission) {
        shPermissionMapper.updateSort();
        return shPermissionMapper.insertSelective(permission);
    }

    @Override
    public Integer updatePermission(ShPermission permission) {
        return shPermissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public Integer deletePermission(Integer id) {
        return shPermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ShPermission getPermissionById(Integer id) {
        return shPermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public ShPermission getPermissionByCode(String permCode, Integer pType) {
        return shPermissionMapper.selectPermissionByCode(permCode, pType);
    }

    @Override
    public ShPermission getPermissionBySort(Integer sort) {
        return shPermissionMapper.selectPermissionBySort(sort);
    }

    @Override
    public List<String> getPermCode(Integer uid, Integer pType) {
        ShUserRole userRole = getUserRoleByUid(uid, pType);
        if (Objects.isNull(userRole)) return null;
        return shPermissionMapper.selectPermCodeById(getRolePermissionByRid(userRole.getRid(), pType));
    }

    @Override
    public List<ShPermission> getPermissionListByPid(Integer pid, Integer pType) {
        return shPermissionMapper.selectPermissionListByPid(pid, pType);
    }

    @Override
    public List<ShPermission> getPermissionListByIdList(List<Integer> idList, Integer pType) {
        return shPermissionMapper.selectPermissionListByIdList(idList, pType);
    }

    @Override
    public Result<ShPermission> getPermissionResult(Query<ShPermission> query) {
        List<ShPermission> list = shPermissionMapper.selectResultByQuery(query);
        Integer total = shPermissionMapper.countResultByQuery(query);
        return new Result<>(true, total, list, null, null);
    }

    @Override
    public Integer addRolePermission(ShRolePermission rolePermission) {
        return shRolePermissionMapper.insertSelective(rolePermission);
    }

    @Override
    public Integer updateRolePermission(ShRolePermission rolePermission) {
        return shRolePermissionMapper.updateByPrimaryKeySelective(rolePermission);
    }

    @Override
    public Integer deleteRolePermission(Integer id) {
        return shRolePermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer deleteRolePermissionByPidAndRid(Integer pid, Integer rid, Integer pType) {
        return shRolePermissionMapper.deleteRolePermissionByPidAndRid(pid, rid, pType);
    }

    @Override
    public ShRolePermission getRolePermissionById(Integer id) {
        return shRolePermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public ShRolePermission getRolePermissionByPidAndRid(Integer pid, Integer rid, Integer pType) {
        return shRolePermissionMapper.selectRolePermissionByPidAndRid(pid, rid, pType);
    }

    @Override
    public List<Integer> getRolePermissionByRid(Integer rid, Integer pType) {
        return shRolePermissionMapper.selectRolePermissionByRid(rid, pType);
    }

    @Override
    public Result<ShRolePermission> getRolePermissionResult(Query<ShRolePermission> query) {
        List<ShRolePermission> list = shRolePermissionMapper.selectResultByQuery(query);
        Integer total = shRolePermissionMapper.countResultByQuery(query);
        return new Result<>(true, total, list, null, null);
    }

    @Override
    public Integer addUserRole(ShUserRole userRole) {
        return shUserRoleMapper.insertSelective(userRole);
    }

    @Override
    public Integer updateUserRole(ShUserRole userRole) {
        return shUserRoleMapper.updateByPrimaryKeySelective(userRole);
    }

    @Override
    public Integer deleteUserRole(Integer id) {
        return shUserRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ShUserRole getUserRoleById(Integer id) {
        return shUserRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public ShUserRole getUserRoleByUid(Integer uid, Integer pType) {
        return shUserRoleMapper.selectUserRoleByUid(uid, pType);
    }

    @Override
    public List<UserMenuVo> getUserMenuVoByUid(Integer uid, Integer pType) {
        ShUserRole userRole = getUserRoleByUid(uid, pType);
        if (Objects.isNull(userRole)) return null;
        List<ShPermission> permissionList = getPermissionListByIdList(getRolePermissionByRid(userRole.getRid(), pType), pType);
        List<UserMenuVo> userMenuVos = new ArrayList<>();
        permissionList.forEach(permission -> {
            UserMenuVo userMenuVo = new UserMenuVo();
            userMenuVo.setId(permission.getId());
            userMenuVo.setIcon(permission.getIcon());
            userMenuVo.setPermCode(permission.getPermcode());
            userMenuVo.setTitle(permission.getTitle());
            userMenuVo.setUrl(permission.getUrl());
            List<ShPermission> permissionLists = getPermissionListByPid(permission.getId(), pType);
            List<UserMenuVo> userMenuVoList = new ArrayList<>();
            permissionLists.forEach(permissions -> {
                ShRolePermission rolePermission = getRolePermissionByPidAndRid(permissions.getId(), userRole.getRid(), pType);
                if (Objects.nonNull(rolePermission)) {
                    UserMenuVo menuVo = new UserMenuVo();
                    menuVo.setId(permissions.getId());
                    menuVo.setIcon(permissions.getIcon());
                    menuVo.setPermCode(permissions.getPermcode());
                    menuVo.setTitle(permissions.getTitle());
                    if (Objects.equals(PermissionMenuType.JAVA_WEB.type, pType)) {
                        menuVo.setUrl("/" + permission.getUrl().replace("/", "")
                                + permissions.getUrl());
                    } else {
                        menuVo.setUrl("/" + permission.getUrl() + permissions.getUrl());
                    }
                    userMenuVoList.add(menuVo);
                }
            });
            userMenuVo.setMvList(userMenuVoList);
            userMenuVos.add(userMenuVo);
        });
        return userMenuVos;
    }

    @Override
    public Result<ShUserRole> getUserRoleResult(Query<ShUserRole> query) {
        List<ShUserRole> list = shUserRoleMapper.selectResultByQuery(query);
        Integer total = shUserRoleMapper.countResultByQuery(query);
        return new Result<>(true, total, list, null, null);
    }
}
