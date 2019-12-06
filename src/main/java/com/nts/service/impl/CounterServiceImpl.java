package com.nts.service.impl;

import com.nts.dao.CounterDao;
import com.nts.entity.Counter;
import com.nts.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    private CounterDao counterDao;

    @Override
    public Map addCounter(Counter counter) {
        counterDao.insertSelective(counter);
        List<Counter> select = counterDao.select(new Counter().setCourseId(counter.getCourseId()));
        Map map = new HashMap();
        map.put("status", "200");
        map.put("counters", select);
        return map;
    }

    @Override
    public Map removeCounter(Counter counter) {
        Map map = new HashMap();
        Counter c = counterDao.selectOne(counter);
        counterDao.delete(counter);
        map.put("status", "200");
        List<Counter> select = counterDao.select(new Counter().setCourseId(c.getCourseId()));
        map.put("counters", select);
        return map;
    }

    @Override
    public Map updateCounter(Counter counter) {
        counterDao.updateByPrimaryKeySelective(counter);
        Map map = new HashMap();
        List<Counter> select = counterDao.select(new Counter().setCourseId(counter.getCourseId()));
        map.put("status", "200");
        map.put("counters", select);
        return map;
    }

    @Override
    public Map show(String id, String uid) {
        List<Counter> counters = counterDao.select(new Counter().setCourseId(id).setUserId(uid));
        Map map = new HashMap();
        map.put("status", "200");
        map.put("counters", counters);
        return map;
    }
}
