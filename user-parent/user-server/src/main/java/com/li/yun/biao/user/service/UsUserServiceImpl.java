package com.li.yun.biao.user.service;

import cn.binarywang.tools.generator.BankCardNumberGenerator;
import cn.binarywang.tools.generator.ChineseIDCardNumberGenerator;
import cn.binarywang.tools.generator.ChineseMobileNumberGenerator;
import cn.binarywang.tools.generator.ChineseNameGenerator;
import com.li.yun.biao.common.dao.AtlasModel;
import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.common.utils.*;
import com.li.yun.biao.goods.api.GoodsService;
import com.li.yun.biao.goods.model.ShGoods;
import com.li.yun.biao.user.api.UsUserService;
import com.li.yun.biao.user.cache.CacheService;
import com.li.yun.biao.user.mapper.ShUserBankCardMapper;
import com.li.yun.biao.user.mapper.ShUserInfoMapper;
import com.li.yun.biao.user.mapper.ShUserLoginRecordMapper;
import com.li.yun.biao.user.model.ShBankCard;
import com.li.yun.biao.user.model.ShUserBankCard;
import com.li.yun.biao.user.model.ShUserInfo;
import com.li.yun.biao.user.model.ShUserLoginRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service("usUserService")
public class UsUserServiceImpl implements UsUserService {
    private Logger logger = LoggerFactory.getLogger(UsUserServiceImpl.class);
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private CacheService cacheService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private ShUserInfoMapper shUserInfoMapper;
    @Resource
    private ShUserBankCardMapper shUserBankCardMapper;
    @Resource
    private ShUserLoginRecordMapper shUserLoginRecordMapper;

    @Override
    public Boolean automaticAddUserInfo() {
        if (new Random().nextInt(4) < 3) return false;
        ShUserInfo userInfo = new ShUserInfo();
        String mobile = ChineseMobileNumberGenerator.getInstance().generate();      //手机号
        String idNumber = ChineseIDCardNumberGenerator.getInstance().generate();    //身份证号
        String password = idNumber.substring(idNumber.length() - 6);
        String name = ChineseNameGenerator.getInstance().generate();
        while (name.length() > 3) {
            name = ChineseNameGenerator.getInstance().generate();
        }
        userInfo.setMobile(mobile);
        userInfo.setName(name);
        userInfo.setPassWord(MD5Util.encode(password));
        userInfo.setIdNumber(idNumber);
        userInfo.setStatus(1);
        userInfo.setLevel(1);
        String ip = MapToolsUtil.getRandomIp();
        AtlasModel atlasModel = MapToolsUtil.getAddressByIp(ip);
        String address = atlasModel.getAddress();
        String lonLat = MapToolsUtil.randomLonLat();
        atlasModel = MapToolsUtil.getAddressByGaoDe(lonLat);
        userInfo.setAddress(address);
        if (Objects.nonNull(atlasModel)) userInfo.setRegisterAddress(atlasModel.toString());
        userInfo.setCreateTime(new Date());
        userInfo.setModifyTime(new Date());
        return addUserInfo(userInfo) > 0;
    }

    @Override
    public Integer addUserInfo(ShUserInfo userInfo) {
        redisUtil.inc("user_register_" + DateUtil.dateToStr(new Date()), 1, DateUtils.addDays(new Date(), 8));
        return shUserInfoMapper.insertSelective(userInfo);
    }

    @Override
    public Integer updateUserInfo(ShUserInfo shUserInfo) {
        return shUserInfoMapper.updateByPrimaryKeySelective(shUserInfo);
    }

    @Override
    public Integer deleteUserInfo(Integer uid) {
        return shUserInfoMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public ShUserInfo getUserInfoByUid(Integer uid) {
        return shUserInfoMapper.selectByPrimaryKey(uid);
    }

    @Override
    public ShUserInfo getUserInfoByMobile(String mobile, String passWord) {
        return shUserInfoMapper.selectUserInfoByMobile(mobile, passWord);
    }

    @Override
    public Result<ShUserInfo> getUserInfoResultByQuery(Query<ShUserInfo> query) {
        List<ShUserInfo> list = shUserInfoMapper.selectResultByQuery(query);
        Integer total = shUserInfoMapper.countResultByQuery(query);
        return new Result<>(true, total, list, null, null);
    }

    @Override
    public Boolean automaticAddUserLoginRecord() {
        if (new Random().nextInt(4) < 2) return false;
        List<ShUserInfo> userInfoList = cacheService.getUserInfoList();
        if (Objects.isNull(userInfoList) || userInfoList.isEmpty()) return false;
        String lonLat = MapToolsUtil.randomLonLat();
        AtlasModel atlasModel = MapToolsUtil.getAddress(lonLat);
        if (Objects.isNull(atlasModel)) atlasModel = MapToolsUtil.getAddressByIp(MapToolsUtil.getRandomIp());
        if (Objects.isNull(atlasModel)) return false;
        ShUserLoginRecord userLoginRecord = new ShUserLoginRecord();
        ShUserInfo userInfo = userInfoList.get(new Random().nextInt(userInfoList.size()));
        userLoginRecord.setUid(userInfo.getUid());
        userLoginRecord.setLoginAdress(atlasModel.toString());
        userLoginRecord.setLoginTime(new Date());
        userLoginRecord.setRealname(userInfo.getName());
        ShGoods shGoods=goodsService.selectGoodsById(2);
        logger.info("*******goods:{}",shGoods.getDescription());
        return addUserLoginRecord(userLoginRecord) > 0;
    }

    @Override
    public Integer addUserLoginRecord(ShUserLoginRecord loginRecord) {
        redisUtil.inc("user_login_" + DateUtil.dateToStr(new Date()), 1, DateUtils.addDays(new Date(), 8));
        return shUserLoginRecordMapper.insertSelective(loginRecord);
    }

    @Override
    public Integer updateUserLoginRecord(ShUserLoginRecord loginRecord) {
        return shUserLoginRecordMapper.updateByPrimaryKeySelective(loginRecord);
    }

    @Override
    public Integer deleteUserLoginRecord(Integer id) {
        return shUserLoginRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ShUserLoginRecord getUserLoginRecord(Integer uid, Integer isLast) {
        return shUserLoginRecordMapper.selectUserLoginRecord(uid, isLast);
    }

    @Override
    public Result<ShUserLoginRecord> getLoginRecordResultByQuery(Query<ShUserLoginRecord> query) {
        List<ShUserLoginRecord> list = shUserLoginRecordMapper.selectResultByQuery(query);
        Integer total = shUserLoginRecordMapper.countResultByQuery(query);
        return new Result<>(true, total, list, null, null);
    }

    @Override
    public Boolean automaticAddUserBankCard() {
        if (new Random().nextBoolean()) return false;
        Random random = new Random();
        List<ShUserInfo> userInfoList = cacheService.getUserInfoList();
        if (Objects.isNull(userInfoList) || userInfoList.isEmpty()) {
            logger.info("添加银行卡异常=====================用户信息获取失败");
            return false;
        }
        ShUserInfo userInfo = userInfoList.get(random.nextInt(userInfoList.size()));
        List<ShBankCard> bankCards = cacheService.getBankCardList();
        if (Objects.isNull(bankCards) || bankCards.isEmpty()) return false;
        ShBankCard bankCard = bankCards.get(random.nextInt(bankCards.size()));
        ShUserBankCard userBankCard = new ShUserBankCard();
        String cardNo = BankCardNumberGenerator.generateByPrefix(Integer.valueOf(bankCard.getBin()));
        userBankCard.setStatus(1);
        userBankCard.setUid(userInfo.getUid());
        userBankCard.setAccountName(userInfo.getName());
        userBankCard.setBankCode(bankCard.getBankCode());
        userBankCard.setBankName(bankCard.getBank());
        userBankCard.setBin(bankCard.getBin());
        userBankCard.setCardName(bankCard.getCardname());
        userBankCard.setCardNo(cardNo);
        userBankCard.setCardType(Objects.equals(bankCard.getCardtype(), "借记卡") ? 1 : Objects.equals(bankCard.getCardtype(), "贷记卡") ? 2 : 3);
        userBankCard.setContactMobile(userInfo.getMobile());
        userBankCard.setIdNumber(userInfo.getIdNumber());
        userBankCard.setCreateTime(new Date());
        userBankCard.setModifyTime(new Date());
        String validDate = "";
        String vcode = "";
        if (Objects.equals(2, userBankCard.getCardType())) {
            Integer year = Integer.valueOf(DateUtil.YearToStr(new Date())) + 5;
            Integer month = random.nextInt(12) + 1;
            if (month < 10) {
                validDate = month + "0" + year;
            } else {
                validDate = validDate + month + year;
            }
            for (int i = 0; i < 3; i++) {
                vcode = vcode + random.nextInt(10);
            }
            userBankCard.setValidDate(validDate);
            userBankCard.setVcode(vcode);
        }
        return addUserBankCard(userBankCard) > 0;
    }

    @Override
    public Integer addUserBankCard(ShUserBankCard userBankCard) {
        return shUserBankCardMapper.insertSelective(userBankCard);
    }

    @Override
    public Integer updateUserBankCard(ShUserBankCard userBankCard) {
        return shUserBankCardMapper.updateByPrimaryKeySelective(userBankCard);
    }

    @Override
    public Integer deleteUserBankCard(Integer id) {
        return shUserBankCardMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ShUserBankCard> getUserBankCardUid(Integer uid) {
        return shUserBankCardMapper.selectUserBankCardUid(uid);
    }

    @Override
    public Result<ShUserBankCard> getUserBankCardResultByQuery(Query<ShUserBankCard> query) {
        List<ShUserBankCard> list = shUserBankCardMapper.selectResultByQuery(query);
        Integer total = shUserBankCardMapper.countResultByQuery(query);
        return new Result<>(true, total, list, null, null);
    }
}
