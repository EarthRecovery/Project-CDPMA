package com.cdpma.common.security.config;

import com.cdpma.common.security.intercepter.HeadInterceptor;
import com.cdpma.common.security.intercepter.TagIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /** 不需要拦截地址 */
    public static final String[] excludeUrls = {"/auth/login", "/login", "/auth/register", "/register"};

    @Autowired
    private HeadInterceptor headInterceptor;

    @Autowired
    private TagIntercepter tagIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(headInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls)
                .order(-10);

        registry.addInterceptor(tagIntercepter)
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls)
                .order(-9);
    }
}
