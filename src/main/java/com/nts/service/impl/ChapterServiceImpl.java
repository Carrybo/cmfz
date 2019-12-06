package com.nts.service.impl;

import com.nts.annotation.LogAnnotation;
import com.nts.dao.ChapterDao;
import com.nts.entity.Chapter;
import com.nts.service.ChapterService;
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
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map findAll(Integer page, Integer rows, String albumId) {
        HashMap hashMap = new HashMap();
        List<Chapter> chapters = chapterDao.selectByRowBounds(new Chapter().setAlbumId(albumId), new RowBounds((page - 1) * rows, rows));
        Integer records = chapterDao.selectCount(new Chapter());
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("rows", chapters);
        hashMap.put("page", page);
        hashMap.put("records", records);
        hashMap.put("total", total);
        return hashMap;
    }

    @Override
    @LogAnnotation("添加专辑章节")
    public Map addChapter(Chapter chapter) {
        HashMap hashMap = new HashMap();
        chapter.setId(UUID.randomUUID().toString());
        chapterDao.insert(chapter);
        hashMap.put("chapterId", chapter.getId());
        return hashMap;
    }

    @Override
    @LogAnnotation("更新专辑章节")
    public void updateChapter(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Override
    @LogAnnotation("删除专辑章节")
    public void removeChapter(Chapter chapter) {
        chapterDao.delete(chapter);
    }

    @Override
    public List<Chapter> show(String albumId) {
        return chapterDao.select(new Chapter().setAlbumId(albumId));
    }
}
