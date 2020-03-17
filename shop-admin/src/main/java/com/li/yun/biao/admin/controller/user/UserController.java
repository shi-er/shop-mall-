package com.li.yun.biao.admin.controller.user;

import com.li.yun.biao.admin.controller.BaseController;
import com.li.yun.biao.common.annotation.Permission;
import com.li.yun.biao.common.dao.OrderBy;
import com.li.yun.biao.common.dao.Page;
import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.common.utils.StringUtil;
import com.li.yun.biao.common.utils.StringUtils;
import com.li.yun.biao.user.api.UsUserService;
import com.li.yun.biao.user.model.ShUserBankCard;
import com.li.yun.biao.user.model.ShUserInfo;
import com.li.yun.biao.user.model.ShUserLoginRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UsUserService usUserService;

    /**
     * 用户列表界面
     *
     * @return
     */
    @Permission("0201")
    @RequestMapping("/base/list")
    public String roleList() {
        return "user/base/list";
    }

    /**
     * 获取用户数据
     *
     * @param mobile
     * @return
     */
    @Permission("0201")
    @ResponseBody
    @RequestMapping("/base/getList")
    public String getList(String mobile) {
        Page<ShUserInfo> page = getPage();
        ShUserInfo userInfo = new ShUserInfo();
        if (StringUtil.isNotBlank(mobile)) userInfo.setMobile(mobile);
        Query<ShUserInfo> query = new Query<>(userInfo, (page.getCurrent() - 1) * page.getSize(),
                page.getSize(), new OrderBy("create_time", true));
        Result<ShUserInfo> result = usUserService.getUserInfoResultByQuery(query);
        page.setRecords((List<ShUserInfo>) result.getDataList());
        page.setTotal((int) result.getTotal());
        return jsonPage(page);
    }

    /**
     * 用户登陆数据界面
     *
     * @return
     */
    @Permission("0202")
    @RequestMapping("/login/list")
    public String loginList() {
        return "user/login/list";
    }

    /**
     * 获取登陆数据
     *
     * @param mobile
     * @return
     */
    @Permission("0202")
    @ResponseBody
    @RequestMapping("/login/getList")
    public String getLoginList(String mobile) {
        Page<ShUserLoginRecord> page = getPage();
        ShUserLoginRecord loginRecord = new ShUserLoginRecord();
        if (StringUtil.isNotBlank(mobile)) {
            ShUserInfo userInfo = usUserService.getUserInfoByMobile(mobile, null);
            if (Objects.nonNull(userInfo)) loginRecord.setUid(userInfo.getUid());
        }
        Query<ShUserLoginRecord> query = new Query<>(loginRecord, (page.getCurrent() - 1) * page.getSize(),
                page.getSize(), new OrderBy("login_time", true));
        Result<ShUserLoginRecord> result = usUserService.getLoginRecordResultByQuery(query);
        page.setRecords((List<ShUserLoginRecord>) result.getDataList());
        page.setTotal((int) result.getTotal());
        return jsonPage(page);
    }

    /**
     * 用户登陆数据界面
     *
     * @return
     */
    @Permission("0203")
    @RequestMapping("/bank/card/list")
    public String baknCardList() {
        return "user/bank/list";
    }

    /**
     * 获取登陆数据
     *
     * @param mobile
     * @return
     */
    @Permission("0203")
    @ResponseBody
    @RequestMapping("/bank/card/getList")
    public String getBankCardList(String mobile, String cardNo) {
        Page<ShUserBankCard> page = getPage();
        ShUserBankCard userBankCard = new ShUserBankCard();
        if (StringUtil.isNotBlank(mobile)) userBankCard.setContactMobile(mobile);
        if (StringUtils.isNotBlank(cardNo)) userBankCard.setCardNo(cardNo);
        Query<ShUserBankCard> query = new Query<>(userBankCard, (page.getCurrent() - 1) * page.getSize(),
                page.getSize(), new OrderBy("modify_time", true));
        Result<ShUserBankCard> result = usUserService.getUserBankCardResultByQuery(query);
        page.setRecords((List<ShUserBankCard>) result.getDataList());
        page.setTotal((int) result.getTotal());
        return jsonPage(page);
    }

}
