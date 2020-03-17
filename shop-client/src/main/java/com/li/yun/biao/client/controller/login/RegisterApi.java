package com.li.yun.biao.client.controller.login;

import com.li.yun.biao.client.common.Response;
import com.li.yun.biao.client.config.ErrorCode;
import com.li.yun.biao.client.controller.BaseController;
import com.li.yun.biao.common.annotation.Action;
import com.li.yun.biao.common.annotation.Permission;
import com.li.yun.biao.common.dao.Token;
import com.li.yun.biao.common.utils.*;
import com.li.yun.biao.user.api.UsUserService;
import com.li.yun.biao.user.model.ShUserInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class RegisterApi extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterApi.class);
    @Resource
    private UsUserService usUserService;
    @Resource
    private RedisUtil redisUtil;

    @Permission(action = Action.Skip)
    @ApiOperation(value = "用户注册", notes = "输入手机号mobile,密码pwd")
    @PostMapping(value = "/register")
    public Response login(@RequestParam String mobile, @RequestParam String pwd, @RequestParam String authCode, HttpServletRequest request, HttpServletResponse response) {
        logger.info("用户注册******mobile:{},pwd:{},authCode", mobile, pwd, authCode);
        if (!RegexUtils.checkMobile(mobile)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_MOBILE_ERROR);
        }
        String redisKey = "redis_key_" + mobile + authCode;
        if (redisUtil.exists(redisKey) && !redisUtil.isExpire(redisKey)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_FREQUENT_REQUESTS_ERROR);
        }
        redisUtil.set(redisKey, "1", DateUtil.addMinute(new Date(), 1));
        if (StringUtils.isBlank(pwd) || pwd.length() < 6) {
            return Response.resp(ErrorCode.API_ERROR_CODE_PWD_ERROR);
        }
        Object saveCode = redisUtil.get("redis_authCode" + mobile);
        if (Objects.isNull(saveCode) || !Objects.equals(String.valueOf(saveCode), authCode)) {
            return Response.respErrorMsg("验证码输入有误");
        }
        ShUserInfo userInfo = usUserService.getUserInfoByMobile(mobile, MD5Util.encode(pwd));
        if (Objects.nonNull(userInfo)) {
            return Response.respErrorMsg("用户已存在，请直接登录");
        }
        userInfo = new ShUserInfo();
        userInfo.setMobile(mobile);
        userInfo.setPassWord(MD5Util.encode(pwd));
        userInfo.setCreateTime(new Date());
        userInfo.setModifyTime(new Date());
        usUserService.addUserInfo(userInfo);
        userInfo = usUserService.getUserInfoByMobile(mobile, null);
        TokenUtil.setToken(response, new Token(userInfo.getUid().toString(), IpHelper.getIpAddr(request)));
        return Response.resp();
    }

    @Permission(action = Action.Skip)
    @ApiOperation(value = "获取注册验证码", notes = "输入手机号mobile,密码pwd")
    @GetMapping(value = "/register/sms")
    public Response login(@RequestParam String mobile) {
        logger.info("获取注册验证码******mobile:{}", mobile);
        if (!RegexUtils.checkMobile(mobile)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_MOBILE_ERROR);
        }
        String redisKey = "redis_key_" + mobile;
        if (redisUtil.exists(redisKey) && !redisUtil.isExpire(redisKey)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_FREQUENT_REQUESTS_ERROR);
        }
        redisUtil.set(redisKey, "1", DateUtil.addMinute(new Date(), 1));
        ShUserInfo userInfo = usUserService.getUserInfoByMobile(mobile, null);
        if (Objects.nonNull(userInfo)) {
            return Response.respErrorMsg("账号已存在，请直接登录");
        }
        String authCode = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            authCode = authCode + random.nextInt(10);
        }
        logger.info("********验证码为authCode:{}", authCode);
        redisUtil.set("redis_authCode" + mobile, authCode, DateUtil.addMinute(new Date(), 5));
        return Response.resp();
    }
}
