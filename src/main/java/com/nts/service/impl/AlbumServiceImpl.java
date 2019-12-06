package com.nts.service.impl;

import com.nts.annotation.LogAnnotation;
import com.nts.dao.AlbumDao;
import com.nts.entity.Album;
import com.nts.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map findAll(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        // jqgrid分页 rows 数据 page 当前页 records 总条数 total 总页数
        List<Album> albums = albumDao.selectByRowBounds(new Album(), new RowBounds((page - 1) * rows, rows));
        int records = albumDao.selectCount(new Album());
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("rows", albums);
        hashMap.put("page", page);
        hashMap.put("records", records);
        hashMap.put("total", total);
        return hashMap;
    }

    @Override
    @LogAnnotation("添加专辑")
    public Map addAlbum(Album album) {
        Map map = new HashMap();
        album.setId(UUID.randomUUID().toString()).setPublishDate(new Date());
        albumDao.insert(album);
        map.put("albumId", album.getId());
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Album findOne(Album album) {
        return albumDao.selectOne(album);
    }

    @Override
    @LogAnnotation("更新专辑")
    public void updateAlbum(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    @LogAnnotation("删除专辑")
    public void removeAlbum(Album album) {
        albumDao.delete(album);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Album> show() {
        return albumDao.select(new Album().setStatus("1"));
    }

    @Override
    public List<Album> showPage() {
        List<Album> albums = albumDao.selectByRowBounds(new Album().setStatus("1"), new RowBounds(0, 6));
        return albums;
    }
}
