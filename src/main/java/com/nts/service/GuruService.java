package com.nts.service;

import com.nts.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    List<Guru> getAllGuru();

    Guru findOne(Guru guru);

    Map findAll(Integer page, Integer rows);

    Map addGuru(Guru guru);

    void updateGuru(Guru guru);

    void removeMany(List<String> ids);
}
