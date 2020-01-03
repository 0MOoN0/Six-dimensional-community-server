package com.sdcommunity.user.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器，判断请求是否带有token，如果有则解析token，最后放行所有请求
 *
 * @author Leon
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO 20200102 Leon Token过期时的异常处理，提醒用户重新登陆或者直接放行
        String athorization = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(athorization)) {
            if (athorization.startsWith("Bearer ")) {
                final String token = athorization.substring(7);
                Claims claims = jwtUtil.parseJWT(token);
                if (claims != null) {
                    // TODO 20200103 Leon：如果角色很多时则需要写很多if-else，能否改用策略模式，或者从数据库中获取
                    if ("admin".equals(claims.get("roles"))) {
                        request.setAttribute("admin_claims", claims);
                    }
                    if ("user".equals(claims.get("roles"))) {
                        request.setAttribute("user_claims", claims);
                    }
                }
            }
        }
        return true;
    }
}
