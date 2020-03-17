package com.li.yun.biao.mq.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConf {
    @Bean(name = "message")
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    @Bean(name = "useradd")
    public Queue queueMessages() {
        return new Queue("topic.useradd");
    }

    @Bean(name = "sign")
    public Queue queueSign() {
        return new Queue("topic.sign");
    }

    @Bean(name = "goodadd")
    public Queue queueGoodAdd() {
        return new Queue("topic.goodadd");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("exchange_shop");
    }

    @Bean
    Binding bindingExchangeMessage(@Qualifier("message") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeUserAdd(@Qualifier("useradd") Queue queueUseradd, TopicExchange exchange) {
        return BindingBuilder.bind(queueUseradd).to(exchange).with("topic.useradd");
    }

    @Bean
    Binding bindingExchangeSign(@Qualifier("sign") Queue queueUseradd, TopicExchange exchange) {
        return BindingBuilder.bind(queueUseradd).to(exchange).with("topic.sign");
    }

    @Bean
    Binding bindingExchangeGoodAdd(@Qualifier("goodadd") Queue queueUseradd, TopicExchange exchange) {
        return BindingBuilder.bind(queueUseradd).to(exchange).with("topic.goodadd");
    }
}