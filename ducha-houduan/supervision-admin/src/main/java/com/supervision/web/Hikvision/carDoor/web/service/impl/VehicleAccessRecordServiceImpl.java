package com.supervision.web.Hikvision.carDoor.web.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.carDoor.web.mapper.VehicleAccessRecordMapper;
import com.supervision.web.Hikvision.carDoor.web.service.VehicleAccessRecordService;
import com.supervision.web.Hikvision.carDoor.web.entity.VehicleAccessRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class VehicleAccessRecordServiceImpl implements VehicleAccessRecordService {

    @Autowired
    private VehicleAccessRecordMapper vehicleAccessRecordMapper;

    @Override
    public PageInfo<VehicleAccessRecord> searchByCondition(Map<String, Object> params) {
        int page = (int) params.getOrDefault("page", 1);
        int size = (int) params.getOrDefault("size", 10);

        Object deviceIdObj = params.get("deviceId");
        Long deviceId = null;
        if (deviceIdObj != null) {
            deviceId = ((Number) deviceIdObj).longValue();
        }

        String plateNumber = (String) params.getOrDefault("plateNumber", null);
        String eventType = (String) params.getOrDefault("eventType", null);
        String startTime = (String) params.getOrDefault("startTime", null);
        String endTime = (String) params.getOrDefault("endTime", null);

        PageHelper.startPage(page, size);

        List<VehicleAccessRecord> list = vehicleAccessRecordMapper.searchByCondition(deviceId, plateNumber, eventType, startTime, endTime);

        return new PageInfo<>(list);
    }

    @Override
    public boolean add(VehicleAccessRecord record) {
        return vehicleAccessRecordMapper.insert(record) > 0;
    }

    @Override
    public boolean update(VehicleAccessRecord record) {
        return vehicleAccessRecordMapper.update(record) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return vehicleAccessRecordMapper.deleteById(id) > 0;
    }

    @Override
    public boolean batchDelete(List<Long> ids) {
        return vehicleAccessRecordMapper.batchDelete(ids) > 0;
    }
}
