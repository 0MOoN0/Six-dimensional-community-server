package com.sdcommunity.qa.client.impl;

import com.sdcommunity.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服务降级
 *
 * @author Leon
 */
@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.REMOTEERROR.getCode(),StatusCode.REMOTEERROR.getMsg());
    }

    @Override
    public Result findAllByIds(List<String> ids) {
        return new Result(false, StatusCode.REMOTEERROR.getCode(),StatusCode.REMOTEERROR.getMsg());
    }
}
