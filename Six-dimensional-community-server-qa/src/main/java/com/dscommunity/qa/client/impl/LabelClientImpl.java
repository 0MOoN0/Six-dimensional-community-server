package com.dscommunity.qa.client.impl;

import com.dscommunity.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @author Leon
 */
@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.REMOTEERROR.getCode(),StatusCode.REMOTEERROR.getMsg());
    }
}
