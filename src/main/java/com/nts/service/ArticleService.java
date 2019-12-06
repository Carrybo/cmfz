package com.nts.service;

import com.nts.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    Map findAll(Integer page, Integer rows);

    Map addArticle(Article article);

    Article findOne(Article article);

    void updateArticle(Article article);

    void removeArticle(Article article);

    List<Article> show();

    List<Article> showPage();
}
