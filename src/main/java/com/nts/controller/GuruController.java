package com.nts.controller;

import com.nts.entity.Article;
import com.nts.entity.Guru;
import com.nts.service.ArticleService;
import com.nts.service.GuruService;
import com.nts.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    private GuruService gs;

    @Autowired
    private ArticleService as;

    @RequestMapping("getAllGuru")
    public List<Guru> getAllGuru() {
        return gs.getAllGuru();
    }

    @RequestMapping("showAllGurus")
    public Map showAllGurus(Integer page, Integer rows) {
        Map all = gs.findAll(page, rows);
        return all;
    }

    @RequestMapping("edit")
    public Map edit(String oper, Guru guru, String[] id) {
        Map hashMap = new HashMap();
        if (oper.equals("add")) {
            hashMap = gs.addGuru(guru);
        }
        if (oper.equals("edit")) {
            guru.setPhoto(null);
            gs.updateGuru(guru);
        }
        if (oper.equals("del")) {
            gs.removeMany(Arrays.asList(id));
            for (String s : id) {
                as.removeArticle(new Article().setGuruId(s));
            }
        }
        return hashMap;
    }

    @RequestMapping("upload")
    public Map upload(MultipartFile photo, String guruId, HttpSession session, HttpServletRequest request) throws IOException {
        String httpUrl = HttpUtil.getHttpUrl(photo, request, session, "/upload/guru/");
        Guru guru = new Guru();
        // 给属性赋值
        guru.setPhoto(httpUrl).setId(guruId);
        System.out.println(guru);
        // 更新数据库
        gs.updateGuru(guru);
        Map map = new HashMap();
        map.put("status", "success");
        return map;
    }
}
