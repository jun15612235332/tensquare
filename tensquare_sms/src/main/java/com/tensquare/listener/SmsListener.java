package com.tensquare.listener;

import com.tensquare.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;

    @RabbitHandler
    public void executeSms(Map<String,String> map){
        String mobile = map.get("mobile");
        String code = map.get("code");
        System.out.println("手机号 : "+mobile);
        System.out.println("验证码 : "+code);
        smsUtil.sendSms(mobile,code);
    }
}
