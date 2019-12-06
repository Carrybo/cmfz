package com.nts.override;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class ImageConverter extends StringImageConverter {
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
        String[] split = value.split("/");
        String imageName = split[split.length - 1];
        String url = "D:\\Baizhi\\Study\\lastProject\\workspace\\cmfz\\src\\main\\webapp\\upload\\img\\" + imageName;
        return new CellData(FileUtils.readFileToByteArray(new File(url)));
    }
}
