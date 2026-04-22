package com.supervision.web.Hikvision.carDoor.web.controller;

import com.supervision.web.Hikvision.carDoor.web.service.CarEventLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/carEvent/log")
public class CarEventLManageController {

    @Resource
    private CarEventLogService carEventLogService;

    /**
     * 获取车辆进出记录列表（带分页、条件筛选）
     *
     * 前端请求示例：
     * GET /car/event/log/list?pageNum=1&pageSize=10&plateNumber=粤A12345
     */
    @GetMapping("/list")
    public Map<String, Object> getCarEventLogs(@RequestParam Map<String, Object> params) {
        Integer pageNum = (params.get("pageNum") != null && !"".equals(params.get("pageNum").toString()))
                ? Integer.parseInt(params.get("pageNum").toString())
                : 1;
        Integer pageSize = (params.get("pageSize") != null && !"".equals(params.get("pageSize").toString()))
                ? Integer.parseInt(params.get("pageSize").toString())
                : 24;

        Long startTime = (params.get("startTime") != null && !"".equals(params.get("startTime").toString()))
                ? Long.parseLong(params.get("startTime").toString())
                : null;

        Long endTime = (params.get("endTime") != null && !"".equals(params.get("endTime").toString()))
                ? Long.parseLong(params.get("endTime").toString())
                : null;
//        Integer pageSize = params.get("pageSize") != null ? Integer.parseInt(params.get("pageSize").toString()) : 24;
//        Long startTime = params.get("startTime") != null ? Long.parseLong(params.get("startTime").toString()) : null;
//        Long endTime = params.get("endTime") != null ? Long.parseLong(params.get("endTime").toString()) : null;
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return carEventLogService.getCarEventLogs(params);
    }

    /**
     * 新增车辆进出记录
     *
     * 前端请求示例：
     * POST /car/event/log/add
     * JSON body:
     * {
     *   "ownerName": "张三",
     *   "plateNumber": "粤A12345",
     *   "phoneNumber": "13800000000",
     *   "eventTime": "2025-10-05T10:00:00",
     *   "eventType": "IN",
     *   "carType": "FIXED",
     *   "carGroup": "A区车库"
     * }
     */
    @PostMapping("/add")
    public String addCarEventLog(@RequestBody Map<String, Object> logData) {
        // 这里你可以根据需要调用 Service 层插入新记录
        // 目前只是占位返回，后续可以扩展
        return "新增车辆进出记录功能待实现";
    }

    /**
     * 删除某条车辆进出记录（按ID）
     *
     * 前端请求示例：
     * DELETE /car/event/log/delete/1
     */
    @DeleteMapping("/delete/{id}")
    public String deleteCarEventLog(@PathVariable("id") Long id) {
        // TODO: 可在此调用 service 层删除逻辑
        return "删除车辆进出记录功能待实现 (id=" + id + ")";
    }
}
