package com.nts;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.nts.entity.DemoData;

import java.util.ArrayList;
import java.util.List;

public class DemoDataListener extends AnalysisEventListener<DemoData> {
    List list = new ArrayList();

    // 每行数据读取完会调用invoke方法
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println(demoData);
        list.add(demoData);
    }

    // 全部读取完毕后执行 doAfterAllAnalysed方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("over");
    }
}
