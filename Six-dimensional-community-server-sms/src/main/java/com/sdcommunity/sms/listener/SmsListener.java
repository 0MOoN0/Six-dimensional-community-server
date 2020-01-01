package com.sdcommunity.sms.listener;

import com.sdcommunity.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Leon
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String templateCode;

    @Value("${aliyun.sms.sign_name}")
    private String signName;

    @RabbitHandler
    public void sendSms(Map<String, String> message){
        String mobile = message.get("mobile");
        String code = message.get("code");
        try {
            smsUtil.sendSms(mobile,templateCode,signName,"{\"code\":\""+code+"\"}");
            System.out.println("Send code: "+code +" to: "+mobile);
        }catch (Exception e){
            // TODO 20191231 Leon：异常处理
            e.printStackTrace();
        }
    }

}
