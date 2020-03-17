package com.li.yun.biao.common.utils;

import com.li.yun.biao.common.dao.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenUtil {

    /**
     * 设置token至cookie
     *
     * @param response
     * @param token
     */
    public static void setToken(HttpServletResponse response, Token token) {
        CookieUtils.setCookie(response, "uid", token.getUid(), CookieUtils.COOKIE_MAX_AGE);
        CookieUtils.setCookie(response, "ip", token.getIp(), CookieUtils.COOKIE_MAX_AGE);
    }

    /**
     * 获取token
     *
     * @param request
     * @return
     */
    public static Token getToken(HttpServletRequest request) {
        String uid = CookieUtils.getCookieValue(request, "uid");
        String ip = CookieUtils.getCookieValue(request, "ip");
        if (StringUtil.isNotBlank(uid)) return new Token(uid, ip);
        return null;
    }

    /**
     * 删除token
     *
     * @param request
     * @param response
     */
    public static void removeToken(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.removeCookie(request, response, "uid");
        CookieUtils.removeCookie(request, response, "ip");
    }
}
