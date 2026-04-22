package com.supervision.grassrootsregistration.domain;

import lombok.Data;

import java.util.Date;

@Data
public class BdglFileBorrowRecord {
    private Long id;
    private String fileNumber;   // 对应主表 number
    private Long borrowId;
    private Date loanTime;
    private Date returnTime;
}
