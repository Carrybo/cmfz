package com.nts.aspect;

import com.google.gson.Gson;
import com.nts.service.UserService;
import io.goeasy.GoEasy;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
public class UserAspect {
    @Autowired
    private UserService us;

    @After("execution(* com.nts.dao.UserDao.insertSelective(..))")
    public Object updateData() {
        // 创建goeasy对象
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-bc65d3307c1e4dd58996a2ae78731c3e");
        // 查询更新的数据
        Map map = new HashMap();
        // 获取注册时间1 7 30 360 内的男生人数
        Integer man1 = us.getCount("男", 1);
        Integer man7 = us.getCount("男", 7);
        Integer man30 = us.getCount("男", 30);
        Integer man360 = us.getCount("男", 360);
        // 获取注册时间1 7 30 360 内的女生人数
        Integer female1 = us.getCount("女", 1);
        Integer female7 = us.getCount("女", 7);
        Integer female30 = us.getCount("女", 30);
        Integer female360 = us.getCount("女", 360);

        // 数据存入数组 然后存入集合传给前台
        Integer[] man = {man1, man7, man30, man360};
        Integer[] female = {female1, female7, female30, female360};
        map.put("man", man);
        map.put("female", female);
        String json = new Gson().toJson(map);
        goEasy.publish("cmfz", json);
        return null;
    }
}
