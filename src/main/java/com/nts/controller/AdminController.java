package com.nts.controller;

import com.nts.entity.Admin;
import com.nts.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService as;

    @RequestMapping("login")
    public String login(Admin admin, HttpServletRequest request, String code) {
        String msg = null;
        HttpSession session = request.getSession();
        String kaptcha = (String) session.getAttribute("code");
        if (code.equals(kaptcha)) {
            try {// 登录成功
                Admin login = as.login(admin);
                session.setAttribute("admin", login.getUsername());
            } catch (Exception e) {// 登录失败
                msg = e.getMessage();
            }
        } else {
            msg = "验证码输入有误！";
        }

        return msg;
    }
}
