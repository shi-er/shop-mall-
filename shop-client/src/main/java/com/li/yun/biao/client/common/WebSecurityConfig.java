package com.li.yun.biao.client.common;

import com.li.yun.biao.client.controller.BaseController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {
    @Bean
    public SSOPermission getSSOPermission() {
        return new SSOPermission();
    }

    @Bean
    public BaseController getBaseController() {
        return new BaseController();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptorPermission = registry.addInterceptor(getSSOPermission());
        InterceptorRegistration addInterceptorBase = registry.addInterceptor(getBaseController());
        // 拦截配置
        addInterceptorPermission.addPathPatterns("/**");
        addInterceptorBase.addPathPatterns("/**");
        //排除拦截
        addInterceptorBase.excludePathPatterns("/register**");
        addInterceptorPermission.excludePathPatterns("/login**");
        addInterceptorBase.excludePathPatterns("/index");
        addInterceptorPermission.excludePathPatterns("/");
    }
}
