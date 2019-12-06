package com.nts.service.impl;

import com.nts.dao.ArticleDao;
import com.nts.entity.Article;
import com.nts.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map findAll(Integer page, Integer rows) {
        Map map = new HashMap();
        List<Article> articles = articleDao.selectByRowBounds(new Article(), new RowBounds((page - 1) * rows, rows));
        int records = articleDao.selectCount(new Article());
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", articles);
        map.put("page", page);
        map.put("records", records);
        map.put("total", total);
        return map;
    }

    @Override
//    @LogAnnotation("添加文章")
    public Map addArticle(Article article) {
        Map map = new HashMap();
        article.setId(UUID.randomUUID().toString());
        articleDao.insert(article);
        map.put("articleId", article.getId());
        return map;
    }

    @Override
    public Article findOne(Article article) {
        return articleDao.selectOne(article);
    }

    @Override
//    @LogAnnotation("更新文章信息")
    public void updateArticle(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
//    @LogAnnotation("删除文章")
    public void removeArticle(Article article) {
        articleDao.delete(article);
    }

    @Override
    public List<Article> show() {
        return articleDao.select(new Article().setStatus("1").setAuthor("通用文章"));
    }

    @Override
    public List<Article> showPage() {
        List<Article> articles = articleDao.selectByRowBounds(new Article().setStatus("1"), new RowBounds(0, 6));
        return articles;
    }


}
