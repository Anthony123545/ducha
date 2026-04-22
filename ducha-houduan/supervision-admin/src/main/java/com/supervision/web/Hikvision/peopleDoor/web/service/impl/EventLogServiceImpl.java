package com.supervision.web.Hikvision.peopleDoor.web.service.impl;

import com.supervision.web.Hikvision.peopleDoor.web.entity.EventLog;
import com.supervision.web.Hikvision.peopleDoor.web.mapper.EventLogMapper;
import com.supervision.web.Hikvision.peopleDoor.web.service.EventLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventLogServiceImpl implements EventLogService {

    @Resource
    private EventLogMapper eventLogMapper;

    @Override
    public Map<String, Object> getEventLogs(Map<String, Object> params) {
        int pageNum = (int) params.getOrDefault("pageNum", 1);
        int pageSize = (int) params.getOrDefault("pageSize", 24);
        int offset = (pageNum - 1) * pageSize;

        List<EventLog> list = eventLogMapper.selectByCondition(
                (String) params.get("eventType"),
                (String) params.get("personId"),
                (String) params.get("jobNumber"),
                (String) params.get("name"),
                (String) params.get("remoteHost"),
                (String) params.get("reader"),
                (String) params.get("detectorId"),
                (String) params.get("operation"),
                (String) params.get("startTime"),
                (String) params.get("endTime"),
                offset, pageSize
        );

        int total = eventLogMapper.countByCondition(
                (String) params.get("eventType"),
                (String) params.get("personId"),
                (String) params.get("jobNumber"),
                (String) params.get("name"),
                (String) params.get("remo  teHost"),
                (String) params.get("reader"),
                (String) params.get("detectorId"),
                (String) params.get("operation"),
                (String) params.get("startTime"),
                (String) params.get("endTime")
        );

        Map<String, Object> result = new HashMap<>();
        result.put("records", list);
        result.put("total", total);
        return result;
    }
}

