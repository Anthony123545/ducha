package com.supervision.web.Hikvision.util;


import com.sun.jna.Structure;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class XStructure extends Structure {
    @Override
    protected List<String> getFieldOrder() {
        List<String> fieldNames = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        // 遍历字段并提取名称
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }
}
