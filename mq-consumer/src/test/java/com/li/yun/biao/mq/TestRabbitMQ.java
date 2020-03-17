package com.li.yun.biao.mq;

import com.li.yun.biao.mq.produc.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(classes = ServerStart.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRabbitMQ {

    private final ExecutorService mqExecutor = Executors.newFixedThreadPool(10);
    @Autowired
    private HelloSender helloSender;

    @Test
    public void testRabbit() {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            mqExecutor.execute(() -> {
                try {
                    helloSender.send("商品" + finalI);
                } catch (Exception e) {
                }
            });
        }
    }
}
