package com.supervision.web.Hikvision.carDoor.web.service;


import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.carDoor.web.entity.VehicleAccessRecord;
import java.util.List;
import java.util.Map;

public interface VehicleAccessRecordService {

    PageInfo<VehicleAccessRecord> searchByCondition(Map<String, Object> params);

    boolean add(VehicleAccessRecord record);

    boolean update(VehicleAccessRecord record);

    boolean delete(Long id);

    boolean batchDelete(List<Long> ids);
}

