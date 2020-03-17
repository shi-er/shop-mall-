package com.li.yun.biao.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.li.yun.biao.admin.cache.CacheService;
import com.li.yun.biao.common.dao.Page;
import com.li.yun.biao.common.dao.Token;
import com.li.yun.biao.common.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class BaseController implements HandlerInterceptor {
    @Resource
    private CacheService cacheService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("======处理请求之前======");
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("========处理请求后，渲染页面前======");
        Integer uid = getUid(request);
        if (isLegalView(modelAndView) && Objects.nonNull(uid)) {
            modelAndView.addObject("post", "interceptor change view before rendering");
            modelAndView.addObject("menuList", cacheService.getUserMenu(uid));
        }

    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //System.out.println("========视图渲染结束了，请求处理完毕====");
    }

    /**
     * 判断是否为合法的视图地址
     * <p>
     *
     * @param modelAndView spring 视图对象
     * @return boolean
     */
    private boolean isLegalView(ModelAndView modelAndView) {
        boolean legal = false;
        if (modelAndView != null) {
            String viewUrl = modelAndView.getViewName();
            if (viewUrl != null && viewUrl.contains("redirect:")) {
                legal = false;
            } else {
                legal = true;
            }
        }
        return legal;
    }

    private Integer getUid(HttpServletRequest request) {
        Token token = TokenUtil.getToken(request);
        if (Objects.nonNull(token)) return Integer.valueOf(token.getUid());
        return null;
    }

    /**
     * <p>
     * 转换为 bootstrap-table 需要的分页格式 JSON
     * </p>
     *
     * @param page 分页对象
     * @return
     */
    protected String jsonPage(Page<?> page) {
        JSONObject jo = new JSONObject();
        jo.put("total", page.getTotal());
        jo.put("rows", page.getRecords());
        return toJson(jo);
    }

    private <T> Page<T> getPage(int size) {
        int _size = size, _index = 1;
        if (request.getParameter("_size") != null) {
            _size = Integer.parseInt(request.getParameter("_size"));
        }
        if (request.getParameter("_index") != null) {
            int _offset = Integer.parseInt(request.getParameter("_index"));
            _index = _offset / _size + 1;
        }
        return new Page<T>(_index, _size);
    }

    /**
     * <p>
     * 获取分页对象
     * </p>
     */
    protected <T> Page<T> getPage() {
        return getPage(15);
    }


    /**
     * 返回 JSON 格式对象
     *
     * @param object 转换对象
     * @return
     */
    private String toJson(Object object) {
        return JSON.toJSONString(object, SerializerFeature.BrowserCompatible);
    }

    /**
     * 返回 JSON 格式对象
     *
     * @param object 转换对象
     * @return
     */
    protected String toJson(Object object, String format) {
        if (format == null) {
            return toJson(object);
        }
        return JSON.toJSONStringWithDateFormat(object, format, SerializerFeature.WriteDateUseDateFormat);
    }

    protected String booleanToString(boolean rlt) {
        return rlt ? "true" : "false";
    }

}
