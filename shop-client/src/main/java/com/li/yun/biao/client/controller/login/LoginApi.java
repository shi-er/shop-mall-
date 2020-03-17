package com.li.yun.biao.client.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.li.yun.biao.client.common.Response;
import com.li.yun.biao.client.config.ErrorCode;
import com.li.yun.biao.client.controller.BaseController;
import com.li.yun.biao.common.annotation.Action;
import com.li.yun.biao.common.annotation.Permission;
import com.li.yun.biao.common.dao.OrderBy;
import com.li.yun.biao.common.dao.Query;
import com.li.yun.biao.common.dao.Result;
import com.li.yun.biao.common.dao.Token;
import com.li.yun.biao.common.utils.*;
import com.li.yun.biao.user.api.UsUserService;
import com.li.yun.biao.user.model.ShUserInfo;
import com.li.yun.biao.user.model.ShUserLoginRecord;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 用户登录
 */
@RestController
@RequestMapping("/api")
public class LoginApi extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LoginApi.class);
    @Resource
    private UsUserService usUserService;
    @Resource
    private RedisUtil redisUtil;

    @Permission(action = Action.Skip)
    @ApiOperation(value = "用户登录", notes = "输入手机号mobile,密码pwd")
    @PostMapping(value = "/login")
    public Response login(@RequestParam String mobile, @RequestParam String pwd, HttpServletRequest request, HttpServletResponse response) {
        logger.info("用户登录******mobile:{},pwd:{}", mobile, pwd);
        if (!RegexUtils.checkMobile(mobile)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_MOBILE_ERROR);
        }
        if (StringUtils.isBlank(pwd) || pwd.length() < 6) {
            return Response.resp(ErrorCode.API_ERROR_CODE_PWD_ERROR);
        }
        ShUserInfo userInfo = usUserService.getUserInfoByMobile(mobile, MD5Util.encode(pwd));
        if (Objects.isNull(userInfo)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_ACCOUNT_PWD_ERROR);
        }
        TokenUtil.setToken(response, new Token(userInfo.getUid().toString(), IpHelper.getIpAddr(request)));
        return Response.resp();
    }


    @Permission(action = Action.Skip)
    @ApiOperation(value = "获取登录验证码", notes = "手机号mobile")
    @GetMapping(value = "/login/sms")
    public Response getLoginSms(@RequestParam String mobile) {
        logger.info("获取登录验证码******mobile:{}", mobile);
        if (!RegexUtils.checkMobile(mobile)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_MOBILE_ERROR);
        }
        String redisKey = "redis_key_" + mobile;
        if (redisUtil.exists(redisKey) && !redisUtil.isExpire(redisKey)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_FREQUENT_REQUESTS_ERROR);
        }
        redisUtil.set(redisKey, "1", DateUtil.addMinute(new Date(), 1));
        ShUserInfo userInfo = usUserService.getUserInfoByMobile(mobile, null);
        if (Objects.isNull(userInfo)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_NO_ACCOUNT_ERROR);
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

    @Permission(action = Action.Skip)
    @ApiOperation(value = "登录验证码", notes = "手机号mobile,验证码authCode")
    @PostMapping(value = "/login/sms")
    public Response loginSms(@RequestParam String mobile, @RequestParam String authCode, HttpServletRequest request, HttpServletResponse response) {
        logger.info("验证码登录******mobile:{},authCode", mobile, authCode);
        if (!RegexUtils.checkMobile(mobile) || StringUtils.isBlank(authCode) || !Objects.equals(authCode.length(), 4)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_INPUT_MSG_ERROR);
        }
        String redisKey = "redis_key_" + mobile + authCode;
        if (redisUtil.exists(redisKey) && !redisUtil.isExpire(redisKey)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_FREQUENT_REQUESTS_ERROR);
        }
        redisUtil.set(redisKey, "1", DateUtil.addMinute(new Date(), 1));
        Object nowCode = redisUtil.get("redis_authCode" + mobile);
        if (Objects.isNull(nowCode) || !Objects.equals(String.valueOf(nowCode), authCode)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_AUTH_CODE_ERROR);
        }
        ShUserInfo userInfo = usUserService.getUserInfoByMobile(mobile, null);
        if (Objects.isNull(userInfo)) {
            return Response.resp(ErrorCode.API_ERROR_CODE_NO_ACCOUNT_ERROR);
        }
        TokenUtil.setToken(response, new Token(userInfo.getUid().toString(), IpHelper.getIpAddr(request)));
        return Response.resp();
    }

    @Permission(action = Action.Skip)
    @ApiOperation(value = "登录记录", notes = "页数page,每页大小size")
    @GetMapping(value = "/login/record")
    public Response loginRecords(@RequestParam(required = false) Integer uid,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size) {
        ShUserLoginRecord loginRecord = new ShUserLoginRecord();
        if (Objects.nonNull(uid)) {
            loginRecord.setUid(uid);
        }
        Result<ShUserLoginRecord> loginRecordResult =
                usUserService.getLoginRecordResultByQuery(new Query<>(loginRecord, (page - 1) * size, size, new OrderBy("login_time", true)));
        Map<String, Object> data = new HashMap<>();
        data.put("total", loginRecordResult.getTotal());
        data.put("page", page);
        data.put("loginData", new ArrayList<HashMap<String, Object>>() {{
            loginRecordResult.getDataList().forEach(record -> {
                add(new HashMap<String, Object>() {{
                    put("id", record.getId());
                    put("uid", record.getUid());
                    put("realName", record.getRealname());
                    put("loginTime", record.getLoginTime());
                    put("loginAdress", JSONObject.parse(record.getLoginAdress()));
                }});
            });
        }});
        return Response.resp(data);
    }

}
