package com.cdpma.common.security.config;

import com.cdpma.common.security.intercepter.HeadInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /** 不需要拦截地址 */
    public static final String[] excludeUrls = {};

    @Autowired
    private HeadInterceptor headInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(headInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls)
                .order(-10);
    }
}
