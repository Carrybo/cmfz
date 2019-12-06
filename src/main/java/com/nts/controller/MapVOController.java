package com.nts.controller;

import com.nts.entity.MapVO;
import com.nts.service.MapVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("map")
public class MapVOController {
    @Autowired
    private MapVOService ms;

    @RequestMapping("getAll")
    public List<MapVO> getAll() {
        List<MapVO> all = ms.findAll();
        return all;
    }
}
