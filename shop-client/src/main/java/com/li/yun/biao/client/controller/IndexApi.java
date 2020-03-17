package com.li.yun.biao.client.controller;

import com.li.yun.biao.client.common.Response;
import com.li.yun.biao.common.dao.AtlasModel;
import com.li.yun.biao.common.utils.IpHelper;
import com.li.yun.biao.common.utils.MapToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class IndexApi {
    private static final Logger logger = LoggerFactory.getLogger(IndexApi.class);

    @GetMapping("/api/index")
    public Response index() {
        logger.info("*******************index");
        return Response.resp("welcome**********index");
    }

    @GetMapping("/")
    public Response welcome(HttpServletRequest request) {
        String ip = IpHelper.getIpAddr(request);
        AtlasModel atlasModel = MapToolsUtil.getAddressByIp(ip);
        if (Objects.nonNull(atlasModel)) {
            logger.info("*******************atlasModel:{}", atlasModel.toString());
        } else {
            logger.info("*******************ip:{}", ip);
        }
        return Response.resp("hello,i'm LiYunBiao");
    }
}
