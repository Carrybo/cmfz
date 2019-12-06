package com.nts;

import com.nts.dao.UserDao;
import com.nts.entity.User;
import com.nts.util.MD5Utils;
import com.nts.util.SendMessage;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserTest extends BasicTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test0() {
        userDao.insert(new User().setId(UUID.randomUUID().toString()).setName("老李"));
    }

    @Test
    public void test1() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            User user = new User().setId(UUID.randomUUID().toString());
            users.add(user);
        }
//                users.forEach(user-> System.out.println(user));
        userDao.insertList(users);
    }

    @Test
    public void test2() {
        List<User> users = userDao.selectByRowBounds(new User(), new RowBounds(1, 10));
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void test3() {
        User user = new User().setId(UUID.randomUUID().toString()).setStatus("正常").setSex("男").setAddress("河南").setRegistDate(new Date());
        userDao.insertSelective(user);
    }

    @Test
    public void test4() {
        Integer count = userDao.countUserRegist("男", 1);
        System.out.println(count);
    }

    @Test
    public void test5() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-82701683c8994633af27847b20182b4b");
        goEasy.publish("conversation", "Hello, GoEasy!");
    }

    @Test
    public void test6() {
        String num = MD5Utils.getNum();
        SendMessage.send("15737496971", num, stringRedisTemplate);
        Sring name = "宁天舒";
    }

}
