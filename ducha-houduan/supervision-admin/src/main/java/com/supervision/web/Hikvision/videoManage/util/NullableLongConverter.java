package com.supervision.web.Hikvision.videoManage.util;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class NullableLongConverter implements Converter<Long> {
    @Override
    public Class supportJavaTypeKey() {
        return Long.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING; // Excel 单元格可以是数字或空字符串
    }

    @Override
    public Long convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String val = cellData.getStringValue();
        if (val == null || val.trim().isEmpty()) {
            return null; // 空值返回 null
        }
        try {
            return Long.valueOf(val.trim());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Excel 数值格式错误: " + val);
        }
    }

    @Override
    public WriteCellData<?> convertToExcelData(Long value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(value == null ? "" : value.toString());
    }
}
