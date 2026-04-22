package com.supervision.common.handler;

import java.util.Arrays;
import java.util.List;

/**
 * List转String字符串转换器
 * @author gaoqiang
 */
public class ListToStringHandler extends ListStringTypeHandler<String> {
    
    private static final String DELIMITER = ",";
    /**
     * List转String实现
     *
     * @param strings List
     * @return String字符串
     */
    @Override
    protected String toStr(List<String> strings) {
        return String.join(DELIMITER, strings);
    }
    
    /**
     * String转List实现
     *
     * @param str 字符串
     * @return List
     */
    @Override
    protected List<String> toList(String str) {
        return Arrays.asList(str.split(DELIMITER));
    }
}
