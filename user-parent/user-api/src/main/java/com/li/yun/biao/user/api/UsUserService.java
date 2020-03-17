package com.li.yun.biao.user.api;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.user.model.ShUserBankCard;
import com.li.yun.biao.user.model.ShUserInfo;
import com.li.yun.biao.user.model.ShUserLoginRecord;

import java.util.List;

/**
 * 用户接口
 */
public interface UsUserService {
    /**
     * 用户信息
     */
    Boolean automaticAddUserInfo();
    Integer addUserInfo(ShUserInfo userInfo);
    Integer updateUserInfo(ShUserInfo shUserInfo);
    Integer deleteUserInfo(Integer uid);
    ShUserInfo getUserInfoByUid(Integer uid);
    ShUserInfo getUserInfoByMobile(String mobile,String passWord);
    Result<ShUserInfo> getUserInfoResultByQuery(Query<ShUserInfo> query);

    /**
     * 用户登陆记录
     */
    Boolean automaticAddUserLoginRecord();
    Integer addUserLoginRecord(ShUserLoginRecord loginRecord);
    Integer updateUserLoginRecord(ShUserLoginRecord loginRecord);
    Integer deleteUserLoginRecord(Integer id);
    ShUserLoginRecord getUserLoginRecord(Integer uid,Integer isLast);
    Result<ShUserLoginRecord> getLoginRecordResultByQuery(Query<ShUserLoginRecord> query);

    /**
     * 用户银行卡信息
     */
    Boolean automaticAddUserBankCard();
    Integer addUserBankCard(ShUserBankCard userBankCard);
    Integer updateUserBankCard(ShUserBankCard userBankCard);
    Integer deleteUserBankCard(Integer id);
    List<ShUserBankCard> getUserBankCardUid(Integer uid);
    Result<ShUserBankCard> getUserBankCardResultByQuery(Query<ShUserBankCard> query);
}
