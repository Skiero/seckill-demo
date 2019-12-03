package com.hik.seckill.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangJinChang on 2019/12/2 15:15
 * WebMvcConf配置类
 */
@Configuration
public class WebMvcConf implements Filter {
    //配置springMVC跨域请求
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedOrigins("http://127.0.0.1:8848")
                        .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                        .maxAge(3600);
            }
        };
    }

    //配置springMVC拦截器
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = request.getHeader("Origin");
        if (origin == null) {
            origin = request.getHeader("Referer");
        }
        // 允许指定域访问跨域资源
        response.setHeader("Access-Control-Allow-Origin", origin);
        // 允许客户端携带跨域cookie，此时origin值不能为“*”，只能为指定单一域名
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if (RequestMethod.OPTIONS.toString().equals(request.getMethod())) {
            String allowMethod = request.getHeader("Access-Control-Request-Method");
            String allowHeaders = request.getHeader("Access-Control-Request-Headers");
            // 浏览器缓存预检请求结果时间,单位:秒
            response.setHeader("Access-Control-Max-Age", "86400");//86400S为24h
            // 允许浏览器在预检请求成功之后发送的实际请求方法名
            response.setHeader("Access-Control-Allow-Methods", allowMethod);
            // 允许浏览器发送的请求消息头
            response.setHeader("Access-Control-Allow-Headers", allowHeaders);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
