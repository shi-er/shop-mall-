package com.li.yun.biao.admin.controller;

import com.li.yun.biao.admin.cache.EhcacheHelper;
import com.li.yun.biao.common.annotation.Action;
import com.li.yun.biao.common.annotation.Permission;
import com.li.yun.biao.common.dao.OrderBy;
import com.li.yun.biao.common.dao.Page;
import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.common.utils.StringUtil;
import com.li.yun.biao.user.api.UsUserService;
import com.li.yun.biao.user.model.ShUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
public class Dashboard extends BaseController {
    @Resource
    private UsUserService usUserService;

    /**
     * 管理后台主界面
     *
     * @return
     */
    @Permission(action = Action.Skip)
    @RequestMapping("/dashboard")
    public String login(Model model) {
        model.addAttribute("userList", usUserService.getUserInfoResultByQuery(new Query<>(new ShUserInfo(), 0,
                15, new OrderBy("create_time", true))).getDataList());
        model.addAttribute("nowTime",new Date());
        return "dashboard";
    }

    @ResponseBody
    @Permission(action = Action.Skip)
    @RequestMapping("/clear/ehcache")
    public String clearEhcache() {
        EhcacheHelper.cleanall();
        return booleanToString(true);
    }
}
