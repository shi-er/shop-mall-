package com.li.yun.biao.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.li.yun.biao.common.utils.HttpUtil;
import com.li.yun.biao.goods.api.GoodsService;
import com.li.yun.biao.user.api.UsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class HelloReceive {
    private Logger logger = LoggerFactory.getLogger(HelloReceive.class);
    @Resource
    private UsUserService usUserService;
    @Resource
    private GoodsService goodsService;

    @RabbitListener(queues = "topic.message")    //监听器监听指定的Queue
    public void processC(String str) {
        logger.info("******Receive:{}", str);
    }

    @RabbitListener(queues = "topic.useradd")    //监听器监听指定的Queue
    public void userAdd(String str) {
        logger.info("******start Receive:{}", str);
        try {
            Thread.sleep(10000);
            logger.info("******end Receive:{}", str);
            if (Objects.equals("userRegister", str)) {
                usUserService.automaticAddUserInfo();
            } else if (Objects.equals("userLogin", str)) {
                usUserService.automaticAddUserLoginRecord();
            } else {
                usUserService.automaticAddUserBankCard();
            }
        } catch (Exception e) {
            logger.info("******topic.useradd Exception Msg:{}", e.getMessage());
        }
    }

    @RabbitListener(queues = "topic.sign")    //监听器监听指定的Queue 签到
    public void sign(String message) {
        logger.info("******start Receive:{}", message);
        JSONObject object = JSON.parseObject(message);
        JSONObject body = new JSONObject();
        body.put("isSignIn", object.getInteger("isSignIn"));
        body.put("origin", object.getInteger("origin"));
        Map<String, String> headers = new HashMap<>();
        headers.put("appId", object.getString("appId"));
        headers.put("token", object.getString("token"));
        try {
            String result = HttpUtil.put(object.getString("url"), body.toJSONString(), headers);
            logger.info("******sign result:{}", result);
        } catch (Exception e) {
            logger.info("******topic.sign Exception Msg:{}", e.getMessage());
        }
    }

    @RabbitListener(queues = "topic.goodadd")    //监听器监听指定的Queue
    public void goodAdd(String str) {
        logger.info("******start goodadd:{}", str);
        try {
            goodsService.automaticAddGoods();
        } catch (Exception e) {
            logger.info("******topic.useradd Exception Msg:{}", e.getMessage());
        }
    }
}
