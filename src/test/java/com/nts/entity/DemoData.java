package com.nts.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ExcelProperty value="字符串标题 "  index -> 指定列号
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoData implements Serializable {
    @ExcelProperty(value = "字符串标题", index = 0)
    private String string;
    @ExcelProperty(value = "日期标题", index = 1)
    private Date date;
    // 这里设置为3 会导致第2列是空的
    @ExcelProperty(value = "数字标题", index = 3)
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
