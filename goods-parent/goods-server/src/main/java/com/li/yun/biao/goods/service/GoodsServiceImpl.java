package com.li.yun.biao.goods.service;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.common.utils.DateUtil;
import com.li.yun.biao.goods.api.GoodsService;
import com.li.yun.biao.goods.cache.CacheService;
import com.li.yun.biao.goods.mapper.*;
import com.li.yun.biao.goods.model.*;
import com.li.yun.biao.goods.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {
    private final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);
    @Resource
    private ShGoodsPhotoWallMapper shGoodsPhotoWallMapper;
    @Resource
    private ShGoodsCategoryMapper shGoodsCategoryMapper;
    @Resource
    private ShGoodsAddressMapper shGoodsAddressMapper;
    @Resource
    private ShGoodsDetailMapper shGoodsDetailMapper;
    @Resource
    private ShGoodsMapper shGoodsMapper;
    @Resource
    private CacheService cacheService;
    @Resource
    private Task task;

    @Override
    public Boolean automaticAddGoods() {
        List<ShGoodsCategory> categoryList = cacheService.getGoodsCategory();
        if (Objects.isNull(categoryList) || categoryList.isEmpty()) {
            logger.info("******分类信息获取失败");
            return false;
        }
        Random random = new Random();
        ShGoodsCategory category = categoryList.get(random.nextInt(categoryList.size()));
        ShGoods shGoods = new ShGoods();
        shGoods.setCategoryId(category.getId());
        Integer total = shGoodsMapper.countResultByQuery(new Query<>(shGoods));
        if (Objects.isNull(total)) total = 0;
        shGoods.setName(category.getName() + total);
        shGoods.setStatus(1);
        shGoods.setSummary(category.getName() + total + "测试");
        shGoods.setDescription(category.getName() + total + "测试" + DateUtil.DateTimeToStr(new Date()));
        total = shGoodsMapper.countResultByQuery(new Query<>(new ShGoods()));
        if (Objects.isNull(total)) total = 0;
        shGoods.setSort(total);
        shGoods.setShowCase(random.nextInt(2));
        shGoods.setInvoice(1);
        shGoods.setRefund(1);
        shGoods.setWarranty(1);
        shGoods.setDiscount((100 - random.nextInt(10)) / 100.0f);
        shGoods.setCreateTime(new Date());
        shGoods.setModifyTime(new Date());
        addGoods(shGoods);
        updateGoodsCategory(category);
        task.addCategoryGoodCount(category.getId(), 1);
        return true;
    }

    @Override
    public Boolean addGoods(ShGoods goods) {
        return shGoodsMapper.insertSelective(goods) > 0;
    }

    @Override
    public Boolean updateGoods(ShGoods goods) {
        return shGoodsMapper.updateByPrimaryKeySelective(goods) > 0;
    }

    @Override
    public ShGoods selectGoodsById(Integer id) {
        return shGoodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public Result<ShGoods> getGoodsResultByQuery(Query<ShGoods> query) {
        List<ShGoods> list = shGoodsMapper.selectResultByQuery(query);
        Integer total = shGoodsMapper.countResultByQuery(query);
        return new Result<>(true, total, list, null, null);
    }

    @Override
    public Boolean automaticAddGoodsCategory() {
        return null;
    }

    @Override
    public Boolean addGoodsCategory(ShGoodsCategory goodsCategory) {
        return shGoodsCategoryMapper.insertSelective(goodsCategory) > 0;
    }

    @Override
    public Boolean updateGoodsCategory(ShGoodsCategory goodsCategory) {
        return shGoodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory) > 0;
    }

    @Override
    public ShGoodsCategory selectGoodsCategoryById(Integer id) {
        return shGoodsCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Result<ShGoodsCategory> getGoodsCategoryResultByQuery(Query<ShGoodsCategory> query) {
        List<ShGoodsCategory> list = shGoodsCategoryMapper.selectResultByQuery(query);
        Integer total = shGoodsCategoryMapper.countResultByQuery(query);
        return new Result<>(true, total, list, null, null);
    }

    @Override
    public Boolean automaticAddGoodsDetail() {
        return null;
    }

    @Override
    public Boolean addGoodsDetail(ShGoodsDetail goodsDetail) {
        return shGoodsDetailMapper.insertSelective(goodsDetail) > 0;
    }

    @Override
    public Boolean updateGoodsDetail(ShGoodsDetail goodsDetail) {
        return shGoodsDetailMapper.updateByPrimaryKeySelective(goodsDetail) > 0;
    }

    @Override
    public ShGoodsDetail selectGoodsDetailById(Integer id) {
        return shGoodsDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ShGoodsDetail> getGoodsDetailList(Integer goodsId) {
        return shGoodsDetailMapper.selectGoodsDetailList(goodsId);
    }

    @Override
    public Result<ShGoodsDetail> getGoodsDetailResultByQuery(Query<ShGoodsDetail> query) {
        List<ShGoodsDetail> list = shGoodsDetailMapper.selectResultByQuery(query);
        Integer total = shGoodsDetailMapper.countResultByQuery(query);
        return new Result<>(true, total, list, null, null);
    }

    @Override
    public Boolean automaticAddGoodsPhotoWall() {
        return null;
    }

    @Override
    public Boolean addGoodsPhotoWall(ShGoodsPhotoWall goodsPhotoWall) {
        return shGoodsPhotoWallMapper.insertSelective(goodsPhotoWall) > 0;
    }

    @Override
    public Boolean updateGoodsPhotoWall(ShGoodsPhotoWall goodsPhotoWall) {
        return shGoodsPhotoWallMapper.updateByPrimaryKeySelective(goodsPhotoWall) > 0;
    }

    @Override
    public ShGoodsPhotoWall selectGoodsPhotoWallById(Integer id) {
        return shGoodsPhotoWallMapper.selectByPrimaryKey(id);
    }

    @Override
    public Result<ShGoodsPhotoWall> getGoodsPhotoWallResultByQuery(Query<ShGoodsPhotoWall> query) {
        List<ShGoodsPhotoWall> list = shGoodsPhotoWallMapper.selectResultByQuery(query);
        Integer total = shGoodsPhotoWallMapper.countResultByQuery(query);
        return new Result<>(true, total, list, null, null);
    }

    @Override
    public Boolean automaticAddGoodsAddress() {
        return null;
    }

    @Override
    public Boolean addGoodsAddress(ShGoodsAddress goodsAddress) {
        return shGoodsAddressMapper.insertSelective(goodsAddress) > 0;
    }

    @Override
    public Boolean updateGoodsAddress(ShGoodsAddress goodsAddress) {
        return shGoodsAddressMapper.updateByPrimaryKeySelective(goodsAddress) > 0;
    }

    @Override
    public Boolean updateGoodsAddressStatus(Integer uid, Integer nowStatus, Integer status, Integer exceptId) {
        return shGoodsAddressMapper.updateGoodsAddressStatus(uid, nowStatus, status, exceptId) > 0;
    }

    @Override
    public ShGoodsAddress selectGoodsAddressById(Integer id) {
        return shGoodsAddressMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ShGoodsAddress> selectGoodsAddressByUid(Integer uid, Integer status) {
        return shGoodsAddressMapper.selectGoodsAddressByUid(uid, status);
    }

    @Override
    public Result<ShGoodsAddress> getGoodsAddressResultByQuery(Query<ShGoodsAddress> query) {
        List<ShGoodsAddress> list = shGoodsAddressMapper.selectResultByQuery(query);
        Integer total = shGoodsAddressMapper.countResultByQuery(query);
        return new Result<>(true, total, list, null, null);
    }
}
