package com.li.yun.biao.user.api;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.user.model.ShBankCard;
import com.li.yun.biao.user.model.ShBankLianhanghao;

import java.util.List;

/**
 * 银行接口
 */
public interface UsBankService {
    /**
     *银行信息
     */
    Integer addBankCard(ShBankCard bankCard);
    Integer updateBankCard(ShBankCard bankCard);
    Integer deleteBankCard(Integer id);
    ShBankCard getBankCardById(Integer id);
    ShBankCard getBankCardByBin(String BIN);
    ShBankCard getBankCardByCardNo(String cardNo);
    Result<ShBankCard> getBankCardResult(Query<ShBankCard> query);

    /**
     * 联行号信息
     */
    Integer addBankLianhanghao(ShBankLianhanghao bankLianhanghao);
    Integer updateBankLianhanghao(ShBankLianhanghao bankLianhanghao);
    Integer deleteBankLianhanghao(Integer id);
    ShBankLianhanghao getBankLianhanghaoById(Integer id);
    List<ShBankLianhanghao> searchBankLianhanghaoList(String bankType, String region, String bankSubName);
    List<ShBankLianhanghao> getBankLianhanghaoList(ShBankLianhanghao lianhanghao);
    ShBankLianhanghao getLianhanghaoByBankNumber(String bankNumber);
    ShBankLianhanghao getLianhanghaoByBankName(String bankName);
    Result<ShBankLianhanghao> getBankLianhanghaoResult(Query<ShBankLianhanghao> query);

}
