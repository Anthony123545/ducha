package com.supervision.web.Hikvision.peopleDoor.web.service;


import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.peopleDoor.web.entity.PersonAccessRecord;

import java.util.List;
import java.util.Map;

public interface PersonAccessRecordService {

    PageInfo<PersonAccessRecord> searchByCondition(Map<String, Object> params);

    boolean add(PersonAccessRecord record);

    boolean update(PersonAccessRecord record);

    boolean deleteById(Long id);

    boolean batchDelete(List<Long> ids);
}

