package com.li.yun.biao.admin.schdule;

import com.li.yun.biao.common.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class SchduleGoods {
    private static final Logger logger = LoggerFactory.getLogger(SchduleGoods.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void addGoods() {
        logger.info("*******topic query:addGoods,time:{}", DateUtil.DateToStr(new Date()));
        rabbitTemplate.convertAndSend("exchange_shop", "goodadd", "addGoods");
    }
}
