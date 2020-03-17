package com.li.yun.biao.client.controller.basic;

import com.li.yun.biao.client.cache.CacheService;
import com.li.yun.biao.client.common.Response;
import com.li.yun.biao.client.config.ErrorCode;
import com.li.yun.biao.client.controller.BaseController;
import com.li.yun.biao.common.annotation.Action;
import com.li.yun.biao.common.annotation.Permission;
import com.li.yun.biao.common.dao.*;
import com.li.yun.biao.common.enums.MenuIcon;
import com.li.yun.biao.common.enums.PermissionMenuType;
import com.li.yun.biao.common.utils.StringUtil;
import com.li.yun.biao.common.utils.TokenUtil;
import com.li.yun.biao.user.api.UsPermissionService;
import com.li.yun.biao.user.model.ShPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/basic/permission")
public class PermissionController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);
    @Resource
    private UsPermissionService permissionService;
    @Resource
    private CacheService cacheService;

    /**
     * 获取权限菜单列表数据
     */
    @Permission("1002")
    @GetMapping("/list")
    public String getList(String permCode, Short type) {
        Page<ShPermission> page = getPage();
        ShPermission permission = new ShPermission();
        permission.setpType(PermissionMenuType.VUE.type);
        if (StringUtil.isNotBlank(permCode)) permission.setPermcode(permCode);
        if (Objects.nonNull(type)) permission.setType(type);
        Query<ShPermission> query = new Query<>(permission, (page.getCurrent() - 1) * page.getSize(),
                page.getSize(), new OrderBy("sort", false));
        Result<ShPermission> result = permissionService.getPermissionResult(query);
        page.setRecords((List<ShPermission>) result.getDataList());
        page.setTotal((int) result.getTotal());
        return jsonPage(page);
    }

    /**
     * 编辑权限菜单
     *
     * @param permission
     * @return
     */
    @Permission("1002")
    @PostMapping("/update")
    public String roleUpdate(ShPermission permission) {
        logger.info("**********permission:{}", permission.toString());
        if (Objects.nonNull(permission.getId())) {
            permissionService.updatePermission(permission);
        } else {
            if (Objects.isNull(permission.getPid())) {
                permission.setPid(0);
            } else {
                permission.setType((short) 1);
            }
            permission.setpType(PermissionMenuType.VUE.type);
            permissionService.addPermission(permission);
        }
        return booleanToString(true);
    }

    @Permission("1002")
    @GetMapping("/icons")
    public List<Map<String, Object>> getIcons() {
        return new ArrayList<Map<String, Object>>() {{
            for (MenuIcon icon : MenuIcon.values()) {
                add(new HashMap<String, Object>() {{
                    put("icon", icon.getIcon());
                    put("name", icon.getIconName());
                }});
            }
        }};
    }

    @Permission("1002")
    @GetMapping("/pids")
    public List<Map<String, Object>> getPids() {
        ShPermission permission = new ShPermission();
        permission.setPid(0);
        permission.setpType(PermissionMenuType.VUE.type);
        Result<ShPermission> permissionResult = permissionService.getPermissionResult(new Query<ShPermission>(permission, 0, 50, new OrderBy("id", true)));
        return new ArrayList<Map<String, Object>>() {{
            for (ShPermission shPermission : permissionResult.getDataList()) {
                add(new HashMap<String, Object>() {{
                    put("id", shPermission.getId());
                    put("name", shPermission.getTitle());
                }});
            }
        }};
    }

    @Permission("1002")
    @DeleteMapping("/pids")
    public String deletePermission(Integer id) {
        logger.info("**********删除permission,id:{}", id);
        Boolean flag = permissionService.deletePermission(id) > 0;
        return booleanToString(flag);
    }

    /**
     * 权限菜单排序
     */
    @Permission("1002")
    @PutMapping("/sort")
    public String sortUpdate(Integer id, Integer sort, Integer status) {
        //0:置顶,1:上移，2:下移
        ShPermission permission = permissionService.getPermissionById(id);
        switch (status) {
            case 0:
                if (!Objects.equals(0, sort)) {
                    permission.setSort(0);
                    permissionService.updatePermission(permission);
                    for (int i = sort - 1; i >= 0; i--) {
                        permission = permissionService.getPermissionBySort(i);
                        if (Objects.nonNull(permission) && !Objects.equals(id, permission.getId())) {
                            permission.setSort(i + 1);
                            permissionService.updatePermission(permission);
                        }
                    }
                }
                break;
            case 1:
                if (!Objects.equals(0, sort)) {
                    ShPermission permission1 = permissionService.getPermissionBySort(sort - 1);
                    if (Objects.nonNull(permission1)) {
                        permission1.setSort(sort);
                        permissionService.updatePermission(permission1);
                    }
                    permission.setSort(sort - 1);
                    permissionService.updatePermission(permission);
                }
                break;
            case 2:
                ShPermission permission1 = permissionService.getPermissionBySort(sort + 1);
                if (Objects.nonNull(permission1)) {
                    permission1.setSort(sort);
                    permissionService.updatePermission(permission1);
                }
                permission.setSort(sort + 1);
                permissionService.updatePermission(permission);
                break;
            default:
                break;
        }
        return booleanToString(true);
    }


    @Permission(action = Action.Skip)
    @GetMapping("/data")
    public Response getUserMenu(HttpServletRequest request) {
        Token token = TokenUtil.getToken(request);
        Integer uid = Objects.nonNull(token) ? Integer.valueOf(token.getUid()) : null;
        if (Objects.isNull(uid)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_NO_PERMISSION_ERROR);
        }
        return Response.resp(cacheService.getUserMenu(uid));
    }
}
