package com.nts;

import com.nts.entity.Guru;
import com.nts.service.GuruService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class GuruTest extends BasicTest {
    @Autowired
    private GuruService gs;

    @Test
    public void test0() {
        Map all = gs.findAll(1, 2);
        List<Guru> rows = (List<Guru>) all.get("rows");
        rows.forEach(row -> System.out.println(row));

    }

    @Test
    public void test1() {
        gs.updateGuru(new Guru().setId("decb9239-f577-4208-af87-6acdd3e7220f").setPhoto("http://192.168.30.1:8989/cmfz/upload/guru/1575186037296_bg3.jpg"));
    }
}
