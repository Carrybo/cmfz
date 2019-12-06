package com.nts.controller;

import com.nts.entity.Album;
import com.nts.entity.Chapter;
import com.nts.service.AlbumService;
import com.nts.service.ChapterService;
import com.nts.util.AudioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService cs;
    @Autowired
    private AlbumService as;

    @RequestMapping("showAllChapters")
    public Map showAll(Integer page, Integer rows, String albumId) {
        Map hashMap = new HashMap();
        if (albumId != null) {
            hashMap = cs.findAll(page, rows, albumId);
        }
        return hashMap;
    }

    @RequestMapping("edit")
    public Map edit(String oper, Chapter chapter, String albumId) {
        Map hashMap = new HashMap();
        if (oper.equals("add")) {
            chapter.setCreateDate(new Date()).setAlbumId(albumId);
            Album one = as.findOne(new Album().setId(albumId));
            int i = one.getCount() + 1;
            one.setCount(i);
            as.updateAlbum(one);
            hashMap = cs.addChapter(chapter);
        }
        if (oper.equals("edit")) {

        }
        if (oper.equals("del")) {
            cs.removeChapter(chapter);
            Album album = new Album().setId(albumId);
            Album one = as.findOne(new Album().setId(albumId));
            int i = one.getCount() - 1;
            one.setCount(i);
            as.updateAlbum(one);
        }
        return hashMap;
    }

    @RequestMapping("upload")
    public Map upload(MultipartFile music, String chapterId, HttpSession session, HttpServletRequest request) throws IOException {
        System.out.println(music + "\t" + chapterId);
        // 获取路径
        String realPath = session.getServletContext().getRealPath("/upload/audio");
        // 判断路径文件是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 防止重名
        String originalFilename = music.getOriginalFilename();
        originalFilename = new Date().getTime() + "_" + originalFilename;
        music.transferTo(new File(realPath, originalFilename));

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
        String uri = http + "://" + localHost.split("/")[1] + ":" + serverPort + contextPath + "/upload/audio/" + originalFilename;

        // 过去上传音频时长与大小
        File file1 = new File(realPath + "/" + originalFilename);
        String time = AudioUtil.getTime(file1);
        String size = AudioUtil.getSize(file1);
        // 更新数据库中信息
        Chapter chapter = new Chapter();
        chapter.setSize(size).setTime(time).setCreateDate(new Date()).setUrl(uri).setId(chapterId);
        cs.updateChapter(chapter);
        HashMap hashMap = new HashMap();
        hashMap.put("status", 200);
        hashMap.put("msg", "添加成功");
        return hashMap;
    }
}
