package com.nts;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.nts.entity.Banner;
import com.nts.entity.DemoData;
import com.nts.service.BannerService;
import com.nts.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class ExcelTest extends BasicTest {
    @Autowired
    private UserService us;
    @Autowired
    private BannerService bs;
    /**
     * 基础导出数据
     */
    private String fileName = "D:\\testExcel\\banner.xlsx";

    /**
     * 生成数据
     */
    private List<DemoData> data() {
        DemoData demoData1 = new DemoData("Ntx", new Date(), 1.0, "Ntx");
        DemoData demoData2 = new DemoData("Ntx", new Date(), 1.0, "Ntx");
        DemoData demoData3 = new DemoData("Ntx", new Date(), 1.0, "Ntx");
        DemoData demoData4 = new DemoData("Ntx", new Date(), 1.0, "Ntx");
        DemoData demoData5 = new DemoData("Ntx", new Date(), 1.0, "Ntx");
        List<DemoData> demoData = Arrays.asList(demoData1, demoData2, demoData3, demoData4, demoData5);
        return demoData;
    }

    @Test
    public void test0() {
        // 写法1
        // EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
        // 写法2
        // List<User> all = us.findAll();
        List<Banner> banners = bs.queryAll();
        ExcelWriter build = EasyExcel.write(fileName, Banner.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("模板2").build();
        build.write(banners, writeSheet);
        // 注意： finish()帮助我们关流
        build.finish();
    }

    /**
     * 基础导入数据
     */
    @Test
    public void test1() {
        //写法一
        //EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
        // 写法2
        ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 注意： 不要忘记关闭 读的时候会创建临时文件，磁盘会崩掉
        excelReader.finish();
    }

    /**
     * 指定列导出数据
     */
    @Test
    public void test2() {
        // 假定要忽略date字段
        // HashSet<String> excludeColumnFileNames = new HashSet<>();
        //excludeColumnFileNames.add("date");
        // 指定需要用哪个class去写
        //EasyExcel.write(fileName, DemoData.class).excludeColumnFiledNames(excludeColumnFileNames).sheet("模板").doWrite(data());

        // 只要导出date字段
        HashSet<String> includeColumnFileNames = new HashSet<>();
        includeColumnFileNames.add("date");
        EasyExcel.write(fileName, DemoData.class).includeColumnFiledNames(includeColumnFileNames).sheet("模板").doWrite(data());

    }

    /**
     * 指定读写的列
     */
    @Test
    public void test3() {

    }
}
