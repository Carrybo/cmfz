package com.nts.service.impl;

import com.nts.dao.GuruDao;
import com.nts.entity.Guru;
import com.nts.service.GuruService;
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
public class GuruServiceImpl implements GuruService {
    @Autowired
    private GuruDao guruDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Guru> getAllGuru() {
        return guruDao.selectAll();
    }

    @Override
    public Guru findOne(Guru guru) {
        return guruDao.selectOne(guru);
    }

    @Override
    public Map findAll(Integer page, Integer rows) {
        Map map = new HashMap();
        List<Guru> gurus = guruDao.selectByRowBounds(new Guru(), new RowBounds((page - 1) * rows, rows));
        Integer records = guruDao.selectCount(new Guru());
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("rows", gurus);
        map.put("page", page);
        map.put("records", records);
        map.put("total", total);
        return map;
    }

    @Override
    public Map addGuru(Guru guru) {
        Map map = new HashMap();
        guru.setId(UUID.randomUUID().toString());
        guruDao.insert(guru);
        map.put("guruId", guru.getId());
        return map;
    }

    @Override
    public void updateGuru(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }

    @Override
    public void removeMany(List<String> ids) {
        guruDao.deleteByIdList(ids);
    }
}
