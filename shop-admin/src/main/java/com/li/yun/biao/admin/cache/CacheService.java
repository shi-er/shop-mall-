package com.li.yun.biao.admin.cache;

import com.li.yun.biao.common.dao.OrderBy;
import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.common.enums.PermissionMenuType;
import com.li.yun.biao.user.api.UsBankService;
import com.li.yun.biao.user.api.UsPermissionService;
import com.li.yun.biao.user.api.UsUserService;
import com.li.yun.biao.user.model.ShBankCard;
import com.li.yun.biao.user.model.ShUserInfo;
import com.li.yun.biao.user.vo.UserMenuVo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Component
public class CacheService {
    @Resource
    private UsBankService usBankService;
    @Resource
    private UsUserService usUserService;
    @Resource
    private UsPermissionService usPermissionService;


    @Cacheable(value = "menusCache", key = "#uid", unless = "#result == null")
    public List<UserMenuVo> getUserMenu(Integer uid) {
        return usPermissionService.getUserMenuVoByUid(uid, PermissionMenuType.JAVA_WEB.type);
    }

    @Cacheable(value = "permissionCache", key = "#uid", unless = "#result == null")
    public List<String> getPermCodes(Integer uid) {
        return usPermissionService.getPermCode(uid, PermissionMenuType.JAVA_WEB.type);
    }

    @Cacheable(value = "userInfoCache", unless = "#result == null")
    public List<ShUserInfo> getUserInfoList() {
        Result<ShUserInfo> result = usUserService.getUserInfoResultByQuery(new Query<>(new ShUserInfo(), 0, 50, new OrderBy("modify_time", false)));
        return (List<ShUserInfo>) result.getDataList();
    }

    @Cacheable(value = "bankCardCache", unless = "#result == null")
    public List<ShBankCard> getBankCardList() {
        Result<ShBankCard> result = usBankService.getBankCardResult(new Query<>(new ShBankCard(), new Random().nextInt(3780), 500, new OrderBy("modifyTime", false)));
        return (List<ShBankCard>) result.getDataList();
    }

}
