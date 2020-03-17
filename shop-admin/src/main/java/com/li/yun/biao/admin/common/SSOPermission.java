package com.li.yun.biao.admin.common;

import com.li.yun.biao.common.annotation.Action;
import com.li.yun.biao.common.annotation.Permission;
import com.li.yun.biao.common.dao.Token;
import com.li.yun.biao.common.enums.PermissionMenuType;
import com.li.yun.biao.common.utils.HttpUtil;
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


    /*
     * 非法请求提示 URL
     */
    private String illegalUrl;

    /**
     * <p>
     * 用户权限验证
     * </p>
     * <p>
     * 方法拦截 Controller 处理之前进行调用。
     * </p>
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            Token token = TokenUtil.getToken(request);
            //token不存在不做拦截
            if (Objects.isNull(token)) return true;
            Integer uid = Integer.valueOf(token.getUid());
            // 权限验证合法
            if (isVerification(request, handler, uid)) return true;
            // 无权限访问
            return unauthorizedAccess(request, response);
        }
        return true;
    }

    /**
     * <p>
     * 判断权限是否合法，支持 1、请求地址 2、注解编码
     * </p>
     *
     * @param request
     * @param handler
     * @param uid
     * @return
     */
    private boolean isVerification(HttpServletRequest request, Object handler, Integer uid) {
        //URL 权限认证
        String uri = request.getRequestURI();
        if (StringUtil.isBlank(uri) || isPermitted(uid, uri)) return true;
        //注解权限认证
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Permission pm = method.getAnnotation(Permission.class);
        // 无Permission也可也访问
        if (Objects.isNull(pm)) return true;
        //忽略拦截
        if (Objects.equals(pm.action(), Action.Skip)) return true;
        //权限合法
        if (StringUtil.isNotBlank(pm.value()) && isPermitted(uid, pm.value())) return true;
        //非法访问
        return false;
    }

    /**
     * <p>
     * 无权限访问处理，默认返回 403 ，illegalUrl 非空重定向至该地址
     * </p>
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    private boolean unauthorizedAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" request 403 url: " + request.getRequestURI());
        if (HttpUtil.isAjax(request)) {
            /* AJAX 请求 403 未授权访问提示 */
            HttpUtil.ajaxStatus(response, 200, "无操作权限");
        } else {
            /* 正常 HTTP 请求 */
            if (illegalUrl == null || "".equals(illegalUrl)) {
                response.sendError(403, "Forbidden");
            } else {
                response.sendRedirect(illegalUrl);
            }
        }
        return false;
    }

    private boolean isPermitted(Integer uid, String permission) {
        //菜单级别、权限验证，生产环境建议加上缓存处理。
        if (StringUtils.isNotBlank(permission)) {
            List<String> pl = usPermissionService.getPermCode(uid, PermissionMenuType.JAVA_WEB.type);
            if (Objects.nonNull(pl) && !pl.isEmpty()) {
                for (String p : pl) {
                    if (permission.equals(p)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getIllegalUrl() {
        return illegalUrl;
    }

    public void setIllegalUrl(String illegalUrl) {
        this.illegalUrl = illegalUrl;
    }
}
