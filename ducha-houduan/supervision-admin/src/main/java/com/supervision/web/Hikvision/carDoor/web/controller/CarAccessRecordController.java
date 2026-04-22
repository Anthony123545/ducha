package com.supervision.web.Hikvision.carDoor.web.controller;


import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.carDoor.web.service.VehicleAccessRecordService;
import com.supervision.web.Hikvision.carDoor.web.entity.VehicleAccessRecord;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/carCamera/accessRecord")
public class CarAccessRecordController {

    @Resource
    private VehicleAccessRecordService vehicleAccessRecordService;

    @PostMapping("/list")
    public Map<String, Object> list(@RequestBody Map<String, Object> params) {
        PageInfo<VehicleAccessRecord> pageInfo = vehicleAccessRecordService.searchByCondition(params);
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pageSize", pageInfo.getPageSize());
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody VehicleAccessRecord record) {
        boolean success = vehicleAccessRecordService.add(record);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("msg", success ? "添加成功" : "添加失败");
        return result;
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody VehicleAccessRecord record) {
        boolean success = vehicleAccessRecordService.update(record);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("msg", success ? "修改成功" : "修改失败");
        return result;
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Map<String, Object> params) {
        List<Long> ids = (List<Long>) params.get("ids");
        boolean success = vehicleAccessRecordService.batchDelete(ids);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("msg", success ? "删除成功" : "删除失败");
        return result;
    }

    @PostMapping("/batchDelete")
    public Map<String, Object> batchDelete(@RequestBody Map<String, Object> params) {
        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) params.get("ids");
        boolean success = vehicleAccessRecordService.batchDelete(ids);
        Map<String,Object> map = new HashMap<>();
        map.put("success", success);
        map.put("msg", success ? "批量删除成功" : "批量删除失败");
        return map;
    }
}
