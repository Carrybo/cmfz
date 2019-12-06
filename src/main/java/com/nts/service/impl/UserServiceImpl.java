package com.nts.service.impl;

import com.nts.dao.UserDao;
import com.nts.entity.User;
import com.nts.service.UserService;
import com.nts.util.MD5Utils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(User user) {
        User login = userDao.selectOne(new User().setTel(user.getTel()));
        if (login == null) throw new RuntimeException("用户不存在！");
        if (!login.getPassword().equals(user.getPassword())) throw new RuntimeException("密码输有误！");
        return login;
    }

    @Override
    public Map regist(User user) {
        // 生成uid
        user.setId(UUID.randomUUID().toString());
        // 添加进数据库
        userDao.insertSelective(user);
        Map map = new HashMap();
        map.put("status", "200");
        map.put("user", user);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map findAll(Integer page, Integer rows) {
        // jqgrid分页  rows 数据 page 当前页 records 总条数 total 总页数
        HashMap hashMap = new HashMap();
        List<User> users = userDao.selectByRowBounds(new User(), new RowBounds((page - 1) * rows, rows));
        Integer records = userDao.selectCount(new User());
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("rows", users);
        hashMap.put("page", page);
        hashMap.put("records", records);
        hashMap.put("total", total);
        return hashMap;
    }

    @Override
    public Map updateStatus(User user) {
        userDao.updateByPrimaryKeySelective(user);
        Map map = new HashMap();
        map.put("status", "success");
        return map;
    }

    @Override
    public User findOne(String id) {
        return userDao.selectOne(new User().setId(id));
    }

    @Override
    public Integer getCount(String sex, Integer day) {
        return userDao.countUserRegist(sex, day);
    }

    @Override
    public List<User> findAll() {
        return userDao.selectAll();
    }

    @Override
    public Map update(User user) {
        // 获取盐
        String salt = MD5Utils.getSalt();
        // 给user赋值
        user.setSalt(salt);
        // 拼接新密码
        String pwdStr = user.getPassword() + salt;
        // md5加密
        String password = MD5Utils.getPassword(pwdStr);
        // 将加密后的密码重新赋值
        user.setPassword(password);
        user.setRegistDate(new Date()).setStatus("正常");
        // 更新数据库中的数据
        userDao.updateByPrimaryKeySelective(user);
        Map map = new HashMap();
        map.put("status", "200");
        map.put("user", user);
        return map;
    }

    @Override
    public User findUserByTel(String tel) {
        return userDao.selectOne(new User().setTel(tel));
    }
}
