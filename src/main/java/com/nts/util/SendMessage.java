package com.nts.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

public class SendMessage {

    public static void send(String tel, String code, StringRedisTemplate stringRedisTemplate) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fd5DDcuxi32dpZG6U58", "3X9uyvpvLWpYeKl0ekRfYBwNojkqXt");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", tel);
        request.putQueryParameter("SignName", "angruy");
        request.putQueryParameter("TemplateCode", "SMS_179610154");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        // 将验证码存入redis
        ValueOperations<String, String> value = stringRedisTemplate.opsForValue();
        value.set(tel, code, 1, TimeUnit.MINUTES);
    }
}