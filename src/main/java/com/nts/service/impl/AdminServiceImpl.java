package com.nts.service.impl;

import com.nts.dao.AdminDao;
import com.nts.entity.Admin;
import com.nts.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin login(Admin admin) {
        Admin login = adminDao.selectOne(new Admin().setUsername(admin.getUsername()));
        if (login == null) throw new RuntimeException("用户名不存在！");
        if (!login.getPassword().equals(admin.getPassword())) throw new RuntimeException("密码输入有误！请重新输入！");
        return login;
    }
}
