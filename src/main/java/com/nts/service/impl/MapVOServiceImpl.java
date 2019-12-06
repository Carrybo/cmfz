package com.nts.service.impl;

import com.nts.dao.MapVODao;
import com.nts.entity.MapVO;
import com.nts.service.MapVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MapVOServiceImpl implements MapVOService {
    @Autowired
    private MapVODao mapVODao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MapVO> findAll() {
        return mapVODao.findAll();
    }
}
