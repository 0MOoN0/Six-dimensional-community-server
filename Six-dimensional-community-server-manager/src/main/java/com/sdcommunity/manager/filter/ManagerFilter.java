package com.sdcommunity.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leon
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }
        // 放行登陆页面
        if(request.getRequestURL().toString().indexOf("/admin/login")>0){
            return null;
        }
        String header = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(header)) {
            if (header.startsWith("Bearer ")) {
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String role = (String) claims.get("roles");
                    if (role.equals("admin")) {
                        // 把头信息转发下去， 并且放行
                        ctx.addZuulRequestHeader("Authorization", header);
                        return null;
                    }
                } catch (Exception e) {
                    // token解析失败，可能是超时或其他因素
                    e.printStackTrace();
                    ctx.setSendZuulResponse(false);  // 终止运行
                }
            }
        }
        ctx.setSendZuulResponse(false);      // 终止运行
        ctx.setResponseStatusCode(403);      // 权限不足
        ctx.setResponseBody("权限不足");
        ctx.getResponse().setContentType("application/json;charset=utf-8");

        return null;
    }
}
