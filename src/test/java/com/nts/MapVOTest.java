package com.nts;

import com.nts.dao.MapVODao;
import com.nts.entity.MapVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MapVOTest extends BasicTest {
    @Autowired
    private MapVODao mapVODao;

    @Test
    public void test0() {
        List<MapVO> all = mapVODao.findAll();
        all.forEach(mapVO -> System.out.println(mapVO));

    }
}
