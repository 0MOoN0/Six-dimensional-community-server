package com.sdcommunity.sms.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Leon
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @RabbitHandler
    public void sendSms(Map<String, String> message){
        System.out.println(message.get("mobile"));
        System.out.println(message.get("code"));
    }

}
