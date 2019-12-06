package com.nts.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nts.override.ImageConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Component
@ContentRowHeight(60)
@ColumnWidth(100 / 8)
public class Banner implements Serializable {
    @Id
    @KeySql(sql = "select uuid()", order = ORDER.BEFORE)
    @ExcelIgnore
    private String id;
    @ExcelProperty("标题")
    private String title;
    @ExcelProperty("描述")
    private String description;
    @ExcelProperty("上传日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    @ExcelProperty(converter = ImageConverter.class, value = "图片")
    private String url;
    @ExcelProperty("链接")
    private String href;
    @ExcelProperty("状态")
    private String status;
}
