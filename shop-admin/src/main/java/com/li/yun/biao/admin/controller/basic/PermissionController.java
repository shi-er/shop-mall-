package com.li.yun.biao.admin.controller.basic;

import com.li.yun.biao.admin.controller.BaseController;
import com.li.yun.biao.common.annotation.Permission;
import com.li.yun.biao.common.dao.OrderBy;
import com.li.yun.biao.common.dao.Page;
import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.common.enums.MenuIcon;
import com.li.yun.biao.common.utils.StringUtil;
import com.li.yun.biao.user.api.UsPermissionService;
import com.li.yun.biao.user.model.ShPermission;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("basic/permission")
public class PermissionController extends BaseController {
    @Resource
    private UsPermissionService permissionService;

    /**
     * 权限菜单列表
     *
     * @return
     */
    @Permission("0101")
    @RequestMapping("/list")
    public String roleList() {
        return "basic/permission/list";
    }

    /**
     * 获取权限菜单列表数据
     *
     * @param permCode
     * @return
     */
    @Permission("0101")
    @ResponseBody
    @RequestMapping("/getList")
    public String getList(String permCode, Short type) {
        Page<ShPermission> page = getPage();
        ShPermission permission = new ShPermission();
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
     * 编辑权限菜单界面
     *
     * @param model
     * @param id
     * @param pid
     * @return
     */
    @Permission("0101")
    @RequestMapping("/edit")
    public String roleEdit(Model model, Integer id, Integer pid) {
        if (Objects.nonNull(id)) {
            model.addAttribute("permission", permissionService.getPermissionById(id));
        }
        model.addAttribute("iconList", MenuIcon.values());
        model.addAttribute("pid", pid);
        return "basic/permission/edit";
    }

    /**
     * 编辑权限菜单
     *
     * @param permission
     * @return
     */
    @Permission("0101")
    @ResponseBody
    @RequestMapping("/update")
    public String roleUpdate(ShPermission permission) {
        if (Objects.nonNull(permission.getId())) {
            permissionService.updatePermission(permission);
        } else {
            if (Objects.isNull(permission.getPid())) {
                permission.setPid(0);
            } else {
                permission.setType((short) 1);
            }
            permissionService.addPermission(permission);
        }
        return booleanToString(true);
    }

    /**
     * 权限菜单排序
     *
     * @param id
     * @param sort
     * @param status
     * @return
     */
    @Permission("0101")
    @ResponseBody
    @RequestMapping("/update/sort")
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
}
