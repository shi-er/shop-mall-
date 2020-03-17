package com.li.yun.biao.goods.task;

import com.li.yun.biao.goods.api.GoodsService;
import com.li.yun.biao.goods.model.ShGoodsCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Component
public class Task {
    private final Logger logger = LoggerFactory.getLogger(Task.class);
    @Resource
    private GoodsService goodsService;

    @Async
    public void addCategoryGoodCount(Integer id, Integer count) {
        ShGoodsCategory category = goodsService.selectGoodsCategoryById(id);
        if (Objects.isNull(category)) return;
        category.setCount(category.getCount() + count);
        category.setModifyTime(new Date());
        goodsService.updateGoodsCategory(category);
    }
}
