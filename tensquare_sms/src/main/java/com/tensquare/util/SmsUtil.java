package com.tensquare.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 阿里云发送短信工具类
 */
public class SmsUtil {
    public static void sendSms(String mobile,String code){
        System.out.println("SmsUtil接收到的手机号"+mobile);
        System.out.println("SmsUtil接收到的验证码"+code);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FwTCksFeycBK8jJ7iwD", "yO2hA2cnrDXE0uwmWkhC09zNFujxlB");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "小深山医院管理系统");
        request.putQueryParameter("TemplateCode", "SMS_184105191");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
