package com.sdcommunity.qa.client;

import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 根据用户ID获取用户信息
 * @author Leon
 */
@Component
@FeignClient("sdcommunity-user")
public interface UserClient {

    @GetMapping("{/id}")
    Result findById(@PathVariable String id);

}
