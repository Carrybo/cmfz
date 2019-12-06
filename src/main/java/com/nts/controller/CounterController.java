package com.nts.controller;

import com.nts.entity.Counter;
import com.nts.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("counter")
public class CounterController {
    @Autowired
    private CounterService cs;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("addCounter")
    public Map addCounter(String uid, Counter counter, String courseId) {
        String cid = UUID.randomUUID().toString();
        counter.setId(cid).setCreateDate(new Date()).setUserId(uid).setCourseId(courseId).setCount(0);
        // 将该计数器的计数存入redis
        ValueOperations<String, String> v = stringRedisTemplate.opsForValue();
        v.set(cid, "0");
        Map map = cs.addCounter(counter);
        return map;
    }

    @RequestMapping("removeCounter")
    public Map removerCounter(String uid, String id) {
        Map map = cs.removeCounter(new Counter().setId(id));
        return map;
    }

    @RequestMapping("updateRedis")
    public Map updateRedis(String uid, String id, Counter counter) {
        ValueOperations<String, String> v = stringRedisTemplate.opsForValue();
        v.set(id + "counter", String.valueOf(counter.getCount() + 1));
        String count = v.get(id + "counter");
        counter.setCount(Integer.valueOf(count));
        Map map = new HashMap();
        map.put("status", "200");
        map.put("counter", counter);
        return map;
    }

    @RequestMapping("lastUpdate")
    public Map lastUpdate(String uid, String id) {
        ValueOperations<String, String> v = stringRedisTemplate.opsForValue();
        String count = v.get(id + "counter");
        Map map = cs.updateCounter(new Counter().setId(id).setUserId(uid).setCount(Integer.valueOf(count)));
        return map;
    }

    @RequestMapping("updateCounter")
    public Map updateCounter(String uid, String id, Integer count) {
        Map map = cs.updateCounter(new Counter().setUserId(uid).setId(id).setCount(count));
        return map;
    }

    @RequestMapping("showAll")
    public Map showAll(String uid, String id) {
        Map show = cs.show(id, uid);
        return show;
    }


}
