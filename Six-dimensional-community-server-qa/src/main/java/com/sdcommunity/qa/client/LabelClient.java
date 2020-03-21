package com.sdcommunity.qa.client;

import com.sdcommunity.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * "@FeignClient("sdcommunity-base")`"：从sdcommunity-base中调用功能
 * @author Leon
 */
@Component
@FeignClient(value = "sdcommunity-base", fallback = LabelClientImpl.class)
public interface LabelClient {
    @GetMapping("/label/{labelId}")
    Result findById(@PathVariable("labelId") String labelId);

    @PostMapping("/label/ids")
    Result findAllByIds(@RequestBody List<String> ids);

}
