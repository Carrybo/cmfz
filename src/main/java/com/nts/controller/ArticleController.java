package com.nts.controller;

import com.nts.entity.Article;
import com.nts.entity.Guru;
import com.nts.service.ArticleService;
import com.nts.service.GuruService;
import com.nts.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService as;

    @Autowired
    private GuruService gs;

    @RequestMapping("showAllArticle")
    public Map showAllArticle(Integer page, Integer rows) {
        Map all = as.findAll(page, rows);
        return all;
    }

    @RequestMapping("uploadImg")
    public Map updateImg(MultipartFile imgFile, String albumId, HttpSession session, HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        String dir = "/upload/articleImg/";
        try {
            String httpUrl = HttpUtil.getHttpUrl(imgFile, request, session, dir);
            hashMap.put("error", 0);
            hashMap.put("url", httpUrl);
        } catch (Exception e) {
            hashMap.put("error", 1);
            hashMap.put("message", "上传错误");
            e.printStackTrace();
        }
        return hashMap;
    }

    @RequestMapping("showAllImges")
    public Map showAllImages(HttpSession session, HttpServletRequest request) {
        // 1. 获取文件夹绝对路径
        String realPath = session.getServletContext().getRealPath("/upload/articleImg");
        // 2. 准备返回的json数据
        Map map = new HashMap();
        List list = new ArrayList();
        // 3. 获取目标文件夹
        File file = new File(realPath);
        File[] files = file.listFiles();
        // 4. 遍历文件夹中的文件
        for (File file1 : files) {
            // 5.文件夹属性的封装
            Map fileMap = new HashMap();
            fileMap.put("is_dir", false);
            fileMap.put("has_file", false);
            fileMap.put("fileSize", file1.length());
            fileMap.put("is_photo", true);
            // 6. 获取文件后缀 | 文件类型
            String extension = FilenameUtils.getExtension(file1.getName());
            fileMap.put("fileType", extension);
            fileMap.put("filename", file1.getName());
            // 7. 获取文件上传时间 1）获取时间戳 2）创建格式转换对象 3）格式转换
            String s = file1.getName().split("_")[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = sdf.format(new Date(Long.valueOf(s)));
            fileMap.put("datetime", format);
            list.add(fileMap);
        }
        map.put("file_list", list);
        map.put("total_count", list.size());
        // 返回路径 项目名+文件夹路径
        map.put("current_url", request.getContextPath() + "/upload/articleImg/");
        return map;
    }

    @RequestMapping("addArticle")
    public Map addArticle(MultipartFile inputfile, Article article, HttpServletRequest request, HttpSession session)
            throws Exception {
        if (!"".equals(inputfile.getOriginalFilename())) {
            // 上传封面
            // 获取路径
            String realPath = session.getServletContext().getRealPath("/upload/articleImg");
            // 判断路径文件是否存在
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            // 防止重名
            String originalFilename = inputfile.getOriginalFilename();
            originalFilename = new Date().getTime() + "_" + originalFilename;
            inputfile.transferTo(new File(realPath, originalFilename));

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
            String uri = http + "://" + localHost.split("/")[1] + ":" + serverPort + contextPath + "/upload/articleImg/" + originalFilename;
            article.setPhoto(uri);
        }
        Guru guru = gs.findOne(new Guru().setId(article.getGuruId()));
        if (guru == null) {
            article.setAuthor("通用文章");
        } else {
            article.setAuthor(guru.getNickName());
        }
        if (article.getId() == null) {
            // 向数据库添加数据 添加
            article.setPublishDate(new Date());
            as.addArticle(article);
        } else {
            // 修改
            as.updateArticle(article);
        }
        Map map = new HashMap();
        map.put("status", "success");
        return map;
    }

    @RequestMapping("findOne")
    public Article findOne(Article article) {
        return as.findOne(article);
    }

    @RequestMapping("edit")
    public void edit(String oper, Article article) {
        if (oper.equals("del")) {
            as.removeArticle(article);
        }
    }
}
