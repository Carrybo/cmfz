package com.nts.service;

import com.nts.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {

    Map findAll(Integer page, Integer rows, String albumId);

    Map addChapter(Chapter chapter);

    void updateChapter(Chapter chapter);

    void removeChapter(Chapter chapter);

    List<Chapter> show(String albumId);
}
