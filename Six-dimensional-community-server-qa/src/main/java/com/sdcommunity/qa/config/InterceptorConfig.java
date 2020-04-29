package com.sdcommunity.qa.config;

import com.sdcommunity.qa.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;

import javax.annotation.Resource;

/**
 * @author Leon
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Resource // Resource注解默认按照ByName的方式注入
    private JwtInterceptor jwtInterceptor;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/**/login/**");
//    }

        @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")     // 拦截的路径
                .excludePathPatterns("/**/login/**");       // 不拦截的路径
            super.addInterceptors(registry);
    }
}
