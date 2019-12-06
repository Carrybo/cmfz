package com.nts.service;

import com.nts.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User login(User user);

    Map regist(User user);

    Map findAll(Integer page, Integer rows);

    Map updateStatus(User user);

    User findOne(String id);

    Integer getCount(String sex, Integer day);

    List<User> findAll();

    Map update(User user);

    User findUserByTel(String tel);
}
