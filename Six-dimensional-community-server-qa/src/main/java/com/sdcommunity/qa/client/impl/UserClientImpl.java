package com.sdcommunity.qa.client.impl;

import com.sdcommunity.qa.client.UserClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * 服务降级，返回远程调用错误
 * @author Leon
 */
@Component
public class UserClientImpl implements UserClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.REMOTEERROR.getCode(), StatusCode.REMOTEERROR.getMsg());
    }
}
