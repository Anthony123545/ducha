package com.supervision.safety.domain;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private long total;
    private int pageNum;
    private int pageSize;
    private List<T> records;

    public PageResult(long total, int pageNum, int pageSize, List<T> records) {
        this.total = total; this.pageNum = pageNum; this.pageSize = pageSize; this.records = records;
    }
}
