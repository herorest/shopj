package com.boot.config;

import com.boot.controller.IndexController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    public CorsConfig(){

    }

    @Bean
    public CorsFilter corsFilter(){
        // 添加配置信息
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");

        // 设置是否发送cookie信息
        config.setAllowCredentials(true);

        // 设置允许请求的方式
        config.addAllowedMethod("*");

        // 设置允许的header
        config.addAllowedHeader("*");

        // 为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", config);    //所有路径

        // 返回重新定义好的source
        return new CorsFilter(corsSource);
    }
}
