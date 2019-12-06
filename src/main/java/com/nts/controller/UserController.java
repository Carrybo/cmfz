package com.nts.controller;

import com.nts.entity.Guru;
import com.nts.entity.User;
import com.nts.service.GuruService;
import com.nts.service.UserService;
import com.nts.util.HttpUtil;
import com.nts.util.MD5Utils;
import com.nts.util.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService us;
    @Autowired
    private GuruService gs;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("findAll")
    public Map findAll(Integer page, Integer rows) {
        Map all = us.findAll(page, rows);
        return all;
    }

    @RequestMapping("updateStatus")
    public Map updateStatus(String userId) {
        Map map = new HashMap();
        User one = us.findOne(userId);
        if ("正常".equals(one.getStatus())) {
            map = us.updateStatus(one.setStatus("冻结"));
        } else {
            map = us.updateStatus(one.setStatus("正常"));
        }
        return map;
    }

    @RequestMapping("getCount")
    public Map getCount() {
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
        return map;
    }

    @RequestMapping("login")
    public Map login(User user, HttpServletRequest request) {
        Map map = new HashMap();
        try {// 登录成功
            User login = us.login(user);
            request.getSession().setAttribute("loginUser", login);
            map.put("status", "200");
            map.put("user", login);
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("sendMessage")
    public Map sendMessage(String tel) {
        Map map = new HashMap();
        try {
            SendMessage.send(tel, MD5Utils.getNum(), stringRedisTemplate);
            map.put("status", "200");
            map.put("message", "验证码发送成功");
        } catch (Exception e) {
            map.put("status", "-200");
            map.put("message", "验证码发送失败");
        }
        return map;
    }

    @RequestMapping("regist")
    public Map regist(User user, String code) {
        Map map = new HashMap();
        // 判断手机号是否注册
        User userByTel = us.findUserByTel(user.getTel());
        if (userByTel != null) {
            map.put("status", "-200");
            map.put("message", "用户名已存在");
        } else {
            // 判断验证码是否正确
            ValueOperations<String, String> value = stringRedisTemplate.opsForValue();
            String str = value.get(user.getTel());
            if (code.equals(str)) {
                map = us.regist(user);
            } else {
                map.put("status", "-200");
                map.put("message", "验证码不正确");
            }

        }
        System.out.println(user);
        return map;
    }

    // 完善个人信息
    @RequestMapping("update")
    public Map update(MultipartFile url, User user, HttpServletRequest request, HttpSession session) {
        if (url != null) {
            // 上传图片
            String httpUrl = HttpUtil.getHttpUrl(url, request, session, "/upload/user/");
            user.setPhoto(httpUrl);
        } else {
            if ("男".equals(user.getSex())) {
                user.setPhoto("http://192.168.30.1:8989/cmfz/upload/user/1575469375339_man.jpg");
            } else {
                user.setPhoto("http://192.168.30.1:8989/cmfz/upload/user/1575469479164_female.jpg");
            }
        }
        Map update = us.update(user);
        return update;
    }

    @RequestMapping("addFollow")
    public Map addFollow(String uid, String id) {
        SetOperations<String, String> set = stringRedisTemplate.opsForSet();
        set.add(uid + "-follow", id);
        Set<String> guruIds = set.members(uid + "-follow");
        List<Guru> gurus = new ArrayList<>();
        guruIds.forEach(guruId -> {
            Guru one = gs.findOne(new Guru().setId(guruId));
            gurus.add(one);
        });
        Map map = new HashMap();
        map.put("status", "200");
        map.put("list", gurus);
        return map;
    }

    @RequestMapping("removeFollow")
    public Map removeFollow(String uid, String id) {
        System.out.println(uid);
        System.out.println(id);
        SetOperations<String, String> set = stringRedisTemplate.opsForSet();
        set.remove(uid + "-follow", id);
        Set<String> guruIds = set.members(uid + "-follow");
        List<Guru> gurus = new ArrayList<>();
        guruIds.forEach(guruId -> {
            Guru one = gs.findOne(new Guru().setId(guruId));
            gurus.add(one);
        });
        Map map = new HashMap();
        map.put("status", "200");
        map.put("list", gurus);
        return map;
    }
}
