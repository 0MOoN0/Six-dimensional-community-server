package com.dscommunity.qa.config;

import com.dscommunity.qa.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * @author Leon
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Resource // Resource注解默认按照ByName的方式注入
    private JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")     // 拦截的路径
                .excludePathPatterns("/**/login/**");       // 不拦截的路径
    }
}
