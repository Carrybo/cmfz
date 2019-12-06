package com.nts.controller;

import com.nts.entity.*;
import com.nts.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("show")
public class ShowController {
    @Autowired
    private BannerService bs;
    @Autowired
    private AlbumService as;
    @Autowired
    private ArticleService ars;
    @Autowired
    private ChapterService cs;
    @Autowired
    private GuruService gs;

    @RequestMapping("showFirst")
    public Map showFirst(String type, String uid, String sub_type) {
        Map map = new HashMap();
        if ("all".equals(type)) {
            List<Banner> showBanner = bs.showPage();
            List<Album> showAlbum = as.showPage();
            List<Article> showArticle = ars.showPage();
            map.put("status", "200");
            map.put("banners", showBanner);
            map.put("albums", showAlbum);
            map.put("articles", showArticle);
        }
        if ("wen".equals(type)) {
            List<Album> show = as.show();
            map.put("status", "200");
            map.put("albums", show);
        }
        if ("si".equals(type)) {
            if ("xmfy".equals(sub_type)) {
                List<Article> show = ars.show();
                map.put("status", "200");
                map.put("articles", show);
            }
        }
        return map;
    }

    @RequestMapping("showOneArticle")
    public Map showOneArticle(String uid, String id) {
        Article one = ars.findOne(new Article().setId(id));
        Map map = new HashMap();
        map.put("status", "200");
        map.put("article", one);
        return map;
    }

    @RequestMapping("showOneAlbum")
    public Map showOneAlbum(String uid, String id) {
        Album one = as.findOne(new Album().setId(id));
        List<Chapter> show = cs.show(id);
        Map map = new HashMap();
        map.put("status", "200");
        map.put("album", one);
        map.put("list", show);
        return map;
    }

    @RequestMapping("showAllGuru")
    public Map showAllGuru() {
        List<Guru> allGuru = gs.getAllGuru();
        Map map = new HashMap();
        map.put("status", "200");
        map.put("list", allGuru);
        return map;
    }
}
