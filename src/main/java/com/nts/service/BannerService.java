package com.nts.service;

import com.nts.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    Map addBanner(Banner banner);

    Map findAll(Integer page, Integer rows);

    Banner findOneById(String id);

    void removeBanner(String id);

    void updateBanner(Banner banner);

    void removeMany(List<String> ids);

    List<Banner> queryAll();

    List<Banner> show();

    List<Banner> showPage();
}
