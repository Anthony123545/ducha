package com.supervision.web.Hikvision.peopleDoor.web.service;

import java.util.Map;

public interface EventLogService {
    Map<String, Object> getEventLogs(Map<String, Object> params);
}
