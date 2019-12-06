package com.nts.service;

import com.nts.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    Map findAll(Integer page, Integer rows);

    Map addAlbum(Album album);

    Album findOne(Album album);

    void updateAlbum(Album album);

    void removeAlbum(Album album);

    List<Album> show();

    List<Album> showPage();
}
