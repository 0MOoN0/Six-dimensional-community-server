package com.sdcommunity.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import utils.JwtUtil;

/**
 * 后台网关
 * @author Leon
 */
@SpringBootApplication
@EnableZuulProxy
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }


    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
