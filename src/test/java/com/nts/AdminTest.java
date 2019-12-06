package com.nts;

import com.nts.dao.AdminDao;
import com.nts.entity.Admin;
import com.nts.service.AdminService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminTest extends BasicTest {
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private AdminService as;

    @Test
    public void test0() {
        List<Admin> admins = adminDao.selectAll();
        admins.forEach(admin -> System.out.println(admin));
    }

    @Test
    public void testLogin() {
        Admin admin = new Admin();
        admin.setUsername("admin").setPassword("admin");
        System.out.println(admin);
        Admin login = as.login(admin);
        System.out.println(login);
    }

}
