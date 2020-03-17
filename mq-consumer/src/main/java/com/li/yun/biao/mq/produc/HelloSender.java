package com.li.yun.biao.mq.produc;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloSender {
    @Autowired
    private AmqpTemplate template;

    public void send(String str) {
        template.convertAndSend("exchange_shop", "topic.message", str);
    }
}
