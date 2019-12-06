package com.nts;

import com.nts.dao.AlbumDao;
import com.nts.entity.Album;
import com.nts.service.AlbumService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class AlbumTest extends BasicTest {
    @Autowired
    private AlbumService as;

    @Autowired
    private AlbumDao albumDao;

    @Test
    public void test0() {
        Map all = as.findAll(1, 1);
        List<Album> rows = (List<Album>) all.get("rows");
        rows.forEach(album -> System.out.println(album));
    }

    @Test
    public void test1() {
        //as.removeAlbum(new Album("1",null,null,null,null,null,null,null,null));
    }

    @Test
    public void test2() {
        Map map = as.addAlbum(new Album());
        map.forEach((k, v) -> System.out.println(k + "\t" + v));
    }

    @Test
    public void test3() {
        albumDao.updateByPrimaryKeySelective(new Album().setId("5ecf183c-2096-4436-9a28-6172a11566a2").setTitle("我奥"));
    }
}
