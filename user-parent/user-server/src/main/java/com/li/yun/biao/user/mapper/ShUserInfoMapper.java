package com.li.yun.biao.user.mapper;

import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.user.model.ShUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShUserInfoMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(ShUserInfo record);

    int insertSelective(ShUserInfo record);

    ShUserInfo selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(ShUserInfo record);

    int updateByPrimaryKey(ShUserInfo record);

    ShUserInfo selectUserInfoByMobile(@Param("mobile") String mobile, @Param("passWord") String passWord);

    List<ShUserInfo> selectResultByQuery(Query<ShUserInfo> query);

    Integer countResultByQuery(Query<ShUserInfo> query);
}