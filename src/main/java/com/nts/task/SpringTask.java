package com.nts.task;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.nts.entity.Banner;
import com.nts.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Async
public class SpringTask {
    @Autowired
    private BannerService bs;

    @Scheduled(cron = "0 0 0 ? * MON")
    public void task01() {
        List<Banner> banners = bs.queryAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "D:\\testExcel\\" + sdf.format(new Date()) + "banner.xlsx";
        ExcelWriter build = EasyExcel.write(fileName, Banner.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("模板2").build();
        build.write(banners, writeSheet);
        build.finish();
    }
}
