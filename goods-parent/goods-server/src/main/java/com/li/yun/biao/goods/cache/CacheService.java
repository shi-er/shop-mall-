package com.li.yun.biao.goods.cache;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.goods.api.GoodsService;
import com.li.yun.biao.goods.model.ShGoodsCategory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CacheService {
    @Resource
    private GoodsService goodsService;

    @Cacheable(value = "goodsCategoryCache", unless = "#result == null")
    public List<ShGoodsCategory> getGoodsCategory() {
        Result<ShGoodsCategory> result = goodsService.getGoodsCategoryResultByQuery(new Query<>(new ShGoodsCategory()));
        return (List<ShGoodsCategory>) result.getDataList();
    }

}
