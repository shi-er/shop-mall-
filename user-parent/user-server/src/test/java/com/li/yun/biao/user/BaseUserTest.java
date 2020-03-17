package com.li.yun.biao.user;


import com.li.yun.biao.common.utils.DateUtil;
import com.li.yun.biao.common.utils.DateUtils;
import com.li.yun.biao.common.utils.MD5Util;
import com.li.yun.biao.common.utils.RedisUtil;
import com.li.yun.biao.user.api.UsUserService;
import com.li.yun.biao.user.model.ShUserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseUserTest {
    private final Logger logger = LoggerFactory.getLogger(BaseUserTest.class);
    @Resource
    private UsUserService usUserService;
    @Resource
    private RedisUtil redisUtil;

    @Test
    public void addUser() {
        ShUserInfo userInfo = new ShUserInfo();
        userInfo.setMobile("18658161306");
        userInfo.setIdNumber("341227198710121517");
        userInfo.setLevel(5);
        userInfo.setName("李云标");
        userInfo.setStatus(1);
        userInfo.setPassWord(MD5Util.encode("123456"));
        userInfo.setCreateTime(new Date());
        userInfo.setModifyTime(new Date());
        usUserService.addUserInfo(userInfo);
    }


    @Test
    public void redisTest() {
        redisUtil.set("123", "hello world");
        System.out.println("进入了方法");
        logger.info("redisLogger------:{}" + redisUtil.get("123").toString());
    }

    @Test
    public void redisInc() {
        String todayKey = DateUtil.dateToStr(new Date()) + "_user_login1";
        for (int i = 0; i < 15; i++) {
            redisUtil.inc(todayKey, 1, DateUtils.addDays(new Date(), 8));
        }

    }
}
