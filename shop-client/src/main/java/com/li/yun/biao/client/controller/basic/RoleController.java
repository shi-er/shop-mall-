package com.li.yun.biao.client.controller.basic;

import com.li.yun.biao.client.controller.BaseController;
import com.li.yun.biao.client.model.RolePermission;
import com.li.yun.biao.client.model.RoleUser;
import com.li.yun.biao.common.annotation.Permission;
import com.li.yun.biao.common.dao.OrderBy;
import com.li.yun.biao.common.dao.Page;
import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.common.enums.PermissionMenuType;
import com.li.yun.biao.common.utils.RegexUtils;
import com.li.yun.biao.common.utils.StringUtil;
import com.li.yun.biao.user.api.UsPermissionService;
import com.li.yun.biao.user.api.UsUserService;
import com.li.yun.biao.user.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/basic/role")
public class RoleController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Resource
    private UsPermissionService permissionService;
    @Resource
    private UsUserService usUserService;


    /**
     * 获取角色数据
     *
     * @param roleId
     * @return
     */
    @Permission("1003")
    @GetMapping("/getList")
    public String getList(Integer roleId) {
        Page<ShRole> page = getPage();
        ShRole shRole = new ShRole();
        shRole.setpType(PermissionMenuType.VUE.type);
        if (Objects.nonNull(roleId)) shRole.setId(roleId);
        Query<ShRole> query = new Query<>(shRole, (page.getCurrent() - 1) * page.getSize(), page.getSize(), new OrderBy("id", true));
        Result<ShRole> result = permissionService.getRoleResult(query);
        page.setRecords((List<ShRole>) result.getDataList());
        page.setTotal((int) result.getTotal());
        return jsonPage(page);
    }


    /**
     * 编辑角色
     *
     * @param shRole
     * @return
     */
    @Permission("1003")
    @PostMapping("/update")
    public String roleEdit(ShRole shRole) {
        if (Objects.nonNull(shRole.getId())) {
            permissionService.updateRole(shRole);
        } else {
            shRole.setpType(PermissionMenuType.VUE.type);
            permissionService.addRole(shRole);
        }
        return booleanToString(true);
    }

    /**
     * 用户权限数据
     *
     * @param permCode
     * @param rid
     * @return
     */
    @Permission("1003")
    @GetMapping("/permission/getList")
    public String getPermissionList(String permCode, Integer rid) {
        Page<ShPermission> page1 = getPage();
        ShPermission permission = new ShPermission();
        permission.setpType(PermissionMenuType.VUE.type);
        if (StringUtil.isNotBlank(permCode)) permission.setPermcode(permCode);
        Query<ShPermission> query = new Query<>(permission, (page1.getCurrent() - 1) * page1.getSize(),
                page1.getSize(), new OrderBy("sort", true));
        Result<ShPermission> result = permissionService.getPermissionResult(query);
        List<RolePermission> permissionList = new ArrayList<>();
        result.getDataList().forEach(p -> {
            RolePermission rolePermission = new RolePermission(p);
            ShRolePermission shRolePermission = permissionService.getRolePermissionByPidAndRid(p.getId(), rid, PermissionMenuType.VUE.type);
            rolePermission.setStatus(0);
            if (Objects.nonNull(shRolePermission)) rolePermission.setStatus(1);
            rolePermission.setParentName("顶级");
            if (!Objects.equals(0, p.getPid())) {
                ShPermission shPermission = permissionService.getPermissionById(p.getId());
                if (Objects.isNull(shPermission)) {
                    rolePermission.setParentName("未授权");
                } else {
                    rolePermission.setParentName(shPermission.getTitle());
                }
            }
            permissionList.add(rolePermission);
        });
        Page<RolePermission> page = getPage();
        page.setRecords(permissionList);
        page.setTotal((int) result.getTotal());
        return jsonPage(page);
    }

    /**
     * 更新用户权限
     *
     * @param id
     * @param rid
     * @param status
     * @return
     */
    @Permission("1003")
    @PutMapping("/permission/update")
    public String permissionUpdate(Integer id, Integer rid, Integer status) {
        //logger.info("**********id:{},rid:{},status:{}", id, rid, status);
        boolean rlt = false;
        if (Objects.equals(status, 1)) {
            ShRolePermission rolePermission = new ShRolePermission();
            rolePermission.setPid(id);
            rolePermission.setRid(rid);
            rolePermission.setpType(PermissionMenuType.VUE.type);
            rlt = permissionService.addRolePermission(rolePermission) > 0;
        } else if (status == 0) {
            rlt = permissionService.deleteRolePermissionByPidAndRid(id, rid, PermissionMenuType.VUE.type) > 0;
        }
        return booleanToString(rlt);
    }


    /**
     * 编辑用户角色
     *
     * @param rid
     * @param status
     * @param mobile
     * @return
     */
    @Permission("1003")
    @PutMapping("/user/update")
    public String userUpdate(Integer rid, Integer status, String mobile) {
        ShUserInfo userInfo = usUserService.getUserInfoByMobile(mobile, null);
        if (Objects.isNull(userInfo)) return "用户不存在!";
        ShUserRole userRole = permissionService.getUserRoleByUid(userInfo.getUid(), PermissionMenuType.VUE.type);
        if (Objects.equals(status, 0)) {
            if (Objects.isNull(userRole)) return booleanToString(true);
            if (!Objects.equals(userRole.getRid(), rid)) return booleanToString(true);
            return booleanToString(permissionService.deleteUserRole(userRole.getId()) > 0);
        } else {
            if (Objects.nonNull(userRole)) {
                if (Objects.equals(rid, userRole.getRid())) return booleanToString(true);
                userRole.setRid(rid);
                return booleanToString(permissionService.updateUserRole(userRole) > 0);
            } else {
                userRole = new ShUserRole();
                userRole.setRid(rid);
                userRole.setUid(userInfo.getUid());
                userRole.setpType(PermissionMenuType.VUE.type);
                return booleanToString(permissionService.addUserRole(userRole) > 0);
            }
        }
    }

    /**
     * 获取角色用户列表
     *
     * @param mobile
     * @return
     */
    @Permission("1004")
    @GetMapping("/user/getList")
    public String getUserList(String mobile) {
        ShUserRole shUserRole = new ShUserRole();
        shUserRole.setpType(PermissionMenuType.VUE.type);
        if (RegexUtils.checkMobile(mobile)) {
            ShUserInfo userInfo = usUserService.getUserInfoByMobile(mobile, null);
            if (Objects.nonNull(userInfo)) shUserRole.setUid(userInfo.getUid());
        }
        Query<ShUserRole> query = new Query<>(shUserRole, (getPage().getCurrent() - 1) * getPage().getSize(), getPage().getSize(), new OrderBy("id", true));
        Result<ShUserRole> result = permissionService.getUserRoleResult(query);
        List<RoleUser> roleUserList = new ArrayList<>();
        result.getDataList().forEach(user -> {
            ShRole role = permissionService.getRoleById(user.getRid());
            ShUserInfo userInfo = usUserService.getUserInfoByUid(user.getUid());
            if (Objects.nonNull(role) && Objects.nonNull(userInfo)) {
                RoleUser roleUser = new RoleUser();
                roleUser.setId(user.getId());
                roleUser.setUid(user.getUid());
                roleUser.setRid(user.getRid());
                roleUser.setMobile(userInfo.getMobile());
                roleUser.setName(userInfo.getName());
                roleUser.setRoleName(role.getName());
                roleUserList.add(roleUser);
            }
        });
        Page<RoleUser> page1 = getPage();
        page1.setRecords(roleUserList);
        page1.setTotal((int) result.getTotal());
        return jsonPage(page1);
    }

    /**
     * 删除用户角色
     *
     * @param id
     * @return
     */
    @Permission("1004")
    @DeleteMapping("/user/delete")
    public String userDelete(Integer id) {
        return booleanToString(permissionService.deleteUserRole(id) > 0);
    }
}
