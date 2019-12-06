package com.nts.controller;

import com.nts.entity.Banner;
import com.nts.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bs;

    @RequestMapping("showAllBanners")
    public Map showAll(Integer page, Integer rows) {
        Map all = bs.findAll(page, rows);
        return all;
    }

    @RequestMapping("edit")
    public Map edit(String oper, Banner banner, String[] id) {
        Map hashMap = new HashMap();
        if (oper.equals("add")) {
            hashMap = bs.addBanner(banner);
        }
        if (oper.equals("edit")) {
            banner.setUrl(null);
            bs.updateBanner(banner);
        }
        if (oper.equals("del")) {
            bs.removeMany(Arrays.asList(id));
        }
        return hashMap;
    }

    @RequestMapping("upload")
    public Map upload(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request) throws IOException {
        // 获取路径
        String realPath = session.getServletContext().getRealPath("/upload/img");
        // 判断路径文件是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 防止重名
        String originalFilename = url.getOriginalFilename();
        originalFilename = new Date().getTime() + "_" + originalFilename;
        url.transferTo(new File(realPath, originalFilename));

        //拼接网络路径
        // 获取网络协议
        String http = request.getScheme();
        // 获取本机ip地址
        String localHost = InetAddress.getLocalHost().toString();
        // 获取服务器端口号
        int serverPort = request.getServerPort();
        // 获取项目名
        String contextPath = request.getContextPath();

        // 网络地址
        String uri = http + "://" + localHost.split("/")[1] + ":" + serverPort + contextPath + "/upload/img/" + originalFilename;

        // 更新数据库中信息
        Banner banner = new Banner();
        banner.setId(bannerId).setUrl(uri);
        bs.updateBanner(banner);
        System.out.println(url);
        System.out.println(bannerId);
        HashMap hashMap = new HashMap();
        hashMap.put("status", 200);
        hashMap.put("msg", "添加成功");
        return hashMap;
    }
}
