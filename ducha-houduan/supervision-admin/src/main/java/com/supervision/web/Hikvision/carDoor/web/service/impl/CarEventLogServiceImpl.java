package com.supervision.web.Hikvision.carDoor.web.service.impl;


import com.supervision.web.Hikvision.carDoor.web.entity.CarEvent;
import com.supervision.web.Hikvision.carDoor.web.mapper.CarEventLogMapper;
import com.supervision.web.Hikvision.carDoor.web.service.CarEventLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarEventLogServiceImpl implements CarEventLogService {

    @Resource
    private CarEventLogMapper carEventLogMapper;

    @Override
    public Map<String, Object> getCarEventLogs(Map<String, Object> params) {
        int pageNum = (int) params.getOrDefault("pageNum", 1);
        int pageSize = (int) params.getOrDefault("pageSize", 24);
        int offset = (pageNum - 1) * pageSize;

        List<CarEvent> list = carEventLogMapper.selectByCondition(
                (String) params.get("plateNumber"),
                (String) params.get("ownerName"),
                (String) params.get("phoneNumber"),
                (String) params.get("eventType"),
                (String) params.get("carType"),
                (String) params.get("carGroup"),
                (String) params.get("startTime"),
                (String) params.get("endTime"),
                offset, pageSize
        );

        int total = carEventLogMapper.countByCondition(
                (String) params.get("plateNumber"),
                (String) params.get("ownerName"),
                (String) params.get("phoneNumber"),
                (String) params.get("eventType"),
                (String) params.get("carType"),
                (String) params.get("carGroup"),
                (String) params.get("startTime"),
                (String) params.get("endTime")
        );

        Map<String, Object> result = new HashMap<>();
        result.put("records", list);
        result.put("total", total);
        return result;
    }
}


