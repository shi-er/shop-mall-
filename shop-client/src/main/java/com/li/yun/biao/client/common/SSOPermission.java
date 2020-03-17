package com.li.yun.biao.client.common;

import com.alibaba.fastjson.JSONObject;
import com.li.yun.biao.common.annotation.Action;
import com.li.yun.biao.common.annotation.Permission;
import com.li.yun.biao.common.dao.Token;
import com.li.yun.biao.common.enums.PermissionMenuType;
import com.li.yun.biao.common.utils.StringUtil;
import com.li.yun.biao.common.utils.TokenUtil;
import com.li.yun.biao.user.api.UsPermissionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * SSO 权限拦截器（必须在 SSO 拦截器之后执行）
 * </p>
 */

public class SSOPermission extends HandlerInterceptorAdapter {
    @Resource
    private UsPermissionService usPermissionService;

    private static final Logger logger = LoggerFactory.getLogger(SSOPermission.class);


    /**
     * 用户权限验证
     * 方法拦截 Controller 处理之前进行调用。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Token token = TokenUtil.getToken(request);
            Integer uid = Objects.nonNull(token) ? Integer.valueOf(token.getUid()) : null;
            //权限验证合法
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Permission pm = method.getAnnotation(Permission.class);
            //无Permission也可访问
            if (Objects.isNull(pm)) {
                return true;
            }
            //忽略拦截
            if (Objects.equals(pm.action(), Action.Skip)) {
                return true;
            }
            //URL权限认证
            String uri = request.getRequestURI();
            logger.info("****************uid:{},uri:{}", uid, uri);
            if (StringUtil.isBlank(uri)) {
                return true;
            }
            //权限合法
            if (StringUtil.isNotBlank(pm.value()) && isPermitted(uid, pm.value())) {
                return true;
            }
            //无权限访问
            return noAuthResp(response);
        }
        return true;
    }


    //菜单级别、权限验证，生产环境建议加上缓存处理。
    private boolean isPermitted(Integer uid, String permission) throws IOException {
        if (StringUtils.isBlank(permission)) {
            return true;
        }
        List<String> pl = usPermissionService.getPermCode(uid, PermissionMenuType.VUE.type);
        if (Objects.isNull(pl) || pl.isEmpty()) {
            return true;
        }
        for (String p : pl) {
            if (Objects.equals(p, permission)) {
                return true;
            }
        }
        return false;
    }

    private boolean noAuthResp(HttpServletResponse response) throws IOException {
        JSONObject object = new JSONObject();
        object.put("code", -1);
        object.put("msg", "您无权限进行该操作");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;UTF-8");
        response.getWriter().write(object.toJSONString());
        return false;
    }
}
