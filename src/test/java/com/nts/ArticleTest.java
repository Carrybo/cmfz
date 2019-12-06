package com.nts;

import com.nts.entity.Article;
import com.nts.service.ArticleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class ArticleTest extends BasicTest {
    @Autowired
    private ArticleService as;

    @Test
    public void test0() {
        as.addArticle(new Article().setId(UUID.randomUUID().toString()).setAuthor("哈啊"));
    }
}
