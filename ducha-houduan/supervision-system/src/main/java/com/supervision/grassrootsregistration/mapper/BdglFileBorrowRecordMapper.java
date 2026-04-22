package com.supervision.grassrootsregistration.mapper;

import com.supervision.grassrootsregistration.domain.BdglFileBorrowRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BdglFileBorrowRecordMapper {
    int batchInsert(@Param("list") List<BdglFileBorrowRecord> list);

    int deleteByFileNumber(String fileNumber);

    List<BdglFileBorrowRecord> selectByFileNumber(String fileNumber);
}
