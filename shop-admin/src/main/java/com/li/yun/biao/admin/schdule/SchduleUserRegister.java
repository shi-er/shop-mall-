package com.li.yun.biao.admin.schdule;

import com.alibaba.fastjson.JSONObject;
import com.li.yun.biao.common.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class SchduleUserRegister {
    private static final Logger logger = LoggerFactory.getLogger(SchduleUserRegister.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void userRegister() {
        logger.info("*******topic query:userRegister,time:{}", DateUtil.DateToStr(new Date()));
        rabbitTemplate.convertAndSend("exchange_shop", "useradd", "userRegister");
    }

    public void userLogin() {
        logger.info("*******topic query:userLogin,time:{}", DateUtil.DateToStr(new Date()));
        rabbitTemplate.convertAndSend("exchange_shop", "useradd", "userLogin");
    }

    public void userBankCardAdd() {
        logger.info("*******topic query:userBankCardAdd,time:{}", DateUtil.DateToStr(new Date()));
        rabbitTemplate.convertAndSend("exchange_shop", "useradd", "userBankCardAdd");
    }

    public void sign() {
        JSONObject sign = new JSONObject();
        sign.put("isSignIn", 1);
        sign.put("origin", 4);
        sign.put("appId", "1000000");
        sign.put("token", "MKdx1Q4qzgqSaIC1mmQG8Q==15357656813f18f626119113970d362948ccf57fa0");
        sign.put("url", "https://mapi.fulapay.com/fula/checkin/signIn");
        logger.info("*******topic query:sign,time:{}", DateUtil.DateToStr(new Date()));
        rabbitTemplate.convertAndSend("exchange_shop", "sign", sign.toJSONString());
    }
}
