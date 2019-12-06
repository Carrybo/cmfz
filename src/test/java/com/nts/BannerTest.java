package com.nts;

import com.nts.entity.Banner;
import com.nts.service.BannerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class BannerTest extends BasicTest {
    @Autowired
    private BannerService bs;

    @Test
    public void test0() {
        Map map = bs.addBanner(new Banner(null, "测fafas ", "这是测试功能2", new Date(), "aaaaa2", "aaaaa2", "是"));
        map.forEach((k, v) -> System.out.println("key:" + k + "  value:" + v));
    }

    @Test
    public void test1() {
        //List<Banner> all = bs.findAll();
        // all.forEach(banner -> System.out.println(banner));
    }

    @Test
    public void test2() {
        Banner oneById = bs.findOneById("3c660428-1031-11ea-808e-2047476ecab8");
        System.out.println(oneById);
    }

    @Test
    public void test3() {
        bs.updateBanner(new Banner().setId("3c660428-1031-11ea-808e-2047476ecab8").setTitle("这是新的测试哈哈哈"));
    }

    @Test
    public void test4() {
        List<Banner> banners = bs.showPage();
        banners.forEach(banner -> System.out.println(banner));
    }
}
