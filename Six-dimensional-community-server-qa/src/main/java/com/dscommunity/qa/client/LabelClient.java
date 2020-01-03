package com.dscommunity.qa.client;

import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * "@FeignClient("sdcommunity-base")`"：从sdcommunity-base中调用功能
 * @author Leon
 */
@Component
@FeignClient("sdcommunity-base")
public interface LabelClient {
    @GetMapping("/label/{labelId}")
    Result findById(@PathVariable("labelId") String labelId);
}
