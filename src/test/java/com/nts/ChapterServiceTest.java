package com.nts;

import com.nts.dao.ChapterDao;
import com.nts.entity.Chapter;
import com.nts.service.ChapterService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ChapterServiceTest extends BasicTest {
    @Autowired
    private ChapterService cs;

    @Autowired
    private ChapterDao chapterDao;

    @Test
    public void test0() {
        Map all = cs.findAll(1, 1, "22");
        List<Chapter> chapters = (List<Chapter>) all.get("rows");
        chapters.forEach(chapter -> System.out.println(chapter));

    }

    @Test
    public void test1() {
        cs.removeChapter(new Chapter().setAlbumId("1"));
    }
}
