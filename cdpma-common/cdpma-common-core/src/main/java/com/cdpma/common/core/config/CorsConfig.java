//package com.cdpma.common.core.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")   // 允许所有请求路径跨域
//                        .allowedOrigins("http://localhost:8080")  // 允许这个前端地址
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许这些请求方法
//                        .allowCredentials(true)  // 如果要携带cookie等信息，必须配置这个
//                        .maxAge(3600);  // 预检请求缓存时间（秒）
//            }
//        };
//    }
//}
