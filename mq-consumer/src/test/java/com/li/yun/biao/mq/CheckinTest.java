package com.li.yun.biao.mq;

import com.alibaba.fastjson.JSONObject;
import com.li.yun.biao.common.utils.HttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = ServerStart.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CheckinTest {
    private Logger logger = LoggerFactory.getLogger(CheckinTest.class);

    @Test
    public void checkin() {
        String url = "https://mapi.fulapay.com/fula/checkin/signIn";
        JSONObject body = new JSONObject();
        body.put("isSignIn", 1);
        body.put("origin", 4);
        Map<String, String> headers = new HashMap<>();
        headers.put("appId", "1000000");
        headers.put("token", "MKdx1Q4qzgqSaIC1mmQG8Q==15357656813f18f626119113970d362948ccf57fa0");
        try {
            String result = HttpUtil.put(url, body.toJSONString(), headers);
            logger.info("******result:{}", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
