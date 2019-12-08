package com.nts.service.impl;

import com.nts.annotation.EditCache;
import com.nts.annotation.LogAnnotation;
import com.nts.annotation.SelectCache;
import com.nts.dao.BannerDao;
import com.nts.entity.Banner;
import com.nts.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    @LogAnnotation("添加轮播图")
    public Map addBanner(Banner banner) {
        Map map = new HashMap();
        banner.setCreateDate(new Date()).setId(UUID.randomUUID().toString());
        bannerDao.insert(banner);
        map.put("bannerId", banner.getId());
        return map;
    }

    @Override
    @SelectCache
    public Map findAll(Integer page, Integer rows) {
        // jqgrid分页  rows 数据 page 当前页 records 总条数 total 总页数
        HashMap hashMap = new HashMap();
        List<Banner> banners = bannerDao.selectByRowBounds(new Banner(), new RowBounds((page - 1) * rows, rows));
        Integer records = bannerDao.selectCount(new Banner());
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("rows", banners);
        hashMap.put("page", page);
        hashMap.put("records", records);
        hashMap.put("total", total);
        return hashMap;
    }


    @Override
    public Banner findOneById(String id) {
        return bannerDao.selectByPrimaryKey(id);
    }

    @Override
    //  @EditCache
    public void removeBanner(String id) {
        bannerDao.deleteByPrimaryKey(id);
    }

    @Override
    @EditCache
    @LogAnnotation("更新轮播图信息")
    public void updateBanner(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    // @EditCache
    @LogAnnotation("删除轮播图或批量删除")
    public void removeMany(List<String> ids) {
        bannerDao.deleteByIdList(ids);
    }

    @Override
    public List<Banner> queryAll() {
        return bannerDao.selectAll();
    }

    @Override
    public List<Banner> show() {
        return bannerDao.select(new Banner().setStatus("1"));
    }

    @Override
    public List<Banner> showPage() {
        List<Banner> banners = bannerDao.selectByRowBounds(new Banner().setStatus("1"), new RowBounds(0, 5));
        return banners;
    }
}
