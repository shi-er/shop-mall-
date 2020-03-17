package com.li.yun.biao.user.service;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.user.api.UsBankService;
import com.li.yun.biao.user.mapper.ShBankCardMapper;
import com.li.yun.biao.user.mapper.ShBankLianhanghaoMapper;
import com.li.yun.biao.user.model.ShBankCard;
import com.li.yun.biao.user.model.ShBankLianhanghao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("usBankService")
public class UsBankServiceImpl implements UsBankService {
    @Resource
    private ShBankCardMapper shBankCardMapper;
    @Resource
    private ShBankLianhanghaoMapper shBankLianhanghaoMapper;

    @Override
    public Integer addBankCard(ShBankCard bankCard) {
        return shBankCardMapper.insertSelective(bankCard);
    }

    @Override
    public Integer updateBankCard(ShBankCard bankCard) {
        return shBankCardMapper.updateByPrimaryKeySelective(bankCard);
    }

    @Override
    public Integer deleteBankCard(Integer id) {
        return shBankCardMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ShBankCard getBankCardById(Integer id) {
        return shBankCardMapper.selectByPrimaryKey(id);
    }

    @Override
    public ShBankCard getBankCardByBin(String BIN) {
        return shBankCardMapper.selectBankCardByBin(BIN);
    }

    @Override
    public ShBankCard getBankCardByCardNo(String cardNo) {
        if (StringUtils.isBlank(cardNo) || cardNo.length() < 6) return null;
        String BIN = cardNo.substring(0, 6);
        return shBankCardMapper.selectByBinAndLen(BIN, cardNo.length());
    }

    @Override
    public Result<ShBankCard> getBankCardResult(Query<ShBankCard> query) {
        List<ShBankCard> list = shBankCardMapper.getResultByQuery(query);
        Integer total = shBankCardMapper.countByQuery(query);
        return new Result<>(true, total, list, null, null);
    }

    @Override
    public Integer addBankLianhanghao(ShBankLianhanghao bankLianhanghao) {
        return shBankLianhanghaoMapper.insertSelective(bankLianhanghao);
    }

    @Override
    public Integer updateBankLianhanghao(ShBankLianhanghao bankLianhanghao) {
        return shBankLianhanghaoMapper.updateByPrimaryKeySelective(bankLianhanghao);
    }

    @Override
    public Integer deleteBankLianhanghao(Integer id) {
        return shBankLianhanghaoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ShBankLianhanghao getBankLianhanghaoById(Integer id) {
        return shBankLianhanghaoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ShBankLianhanghao> searchBankLianhanghaoList(String bankType, String region, String bankSubName) {
        return shBankLianhanghaoMapper.select("%" + bankType + "%", "%" + region + "%", "%" + bankSubName + "%");
    }

    @Override
    public List<ShBankLianhanghao> getBankLianhanghaoList(ShBankLianhanghao lianhanghao) {
        return shBankLianhanghaoMapper.selectListByLianhanghao(lianhanghao);
    }

    @Override
    public ShBankLianhanghao getLianhanghaoByBankNumber(String bankNumber) {
        return shBankLianhanghaoMapper.selectByBankNumber(bankNumber);
    }

    @Override
    public ShBankLianhanghao getLianhanghaoByBankName(String bankName) {
        return shBankLianhanghaoMapper.selectByBankName(bankName);
    }

    @Override
    public Result<ShBankLianhanghao> getBankLianhanghaoResult(Query<ShBankLianhanghao> query) {
        List<ShBankLianhanghao> list = shBankLianhanghaoMapper.getResultByQuery(query);
        Integer total = shBankLianhanghaoMapper.countByQuery(query);
        return new Result<>(true, total, list, null, null);
    }
}
