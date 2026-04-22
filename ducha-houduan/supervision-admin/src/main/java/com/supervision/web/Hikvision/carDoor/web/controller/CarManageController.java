package com.supervision.web.Hikvision.carDoor.web.controller;


import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.carDoor.web.entity.CarInfo;
import com.supervision.web.Hikvision.carDoor.web.service.CarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/car")
public class CarManageController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CarManageController.class);

    @Autowired
    private CarInfoService carInfoService;

    // 查询车辆列表
    @PostMapping("/list")
    public Map<String, Object> list(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            PageInfo<CarInfo> pageInfo = carInfoService.searchByCondition(params);
            result.put("total", pageInfo.getTotal());
            result.put("list", pageInfo.getList());
            result.put("pageNum", pageInfo.getPageNum());
            result.put("pageSize", pageInfo.getPageSize());
            result.put("status", true);
            result.put("message", "获取所有设备列表成功");
        } catch (Exception e) {
            logger.error("获取所有设备列表失败", e);
            result.put("status", false);
            result.put("message", "获取所有设备列表失败");
        }
        return result;
    }

    // 添加车辆
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody @Valid CarInfo carInfo) {
        Map<String, Object> result = new HashMap<>();
        try {
            carInfoService.add(carInfo);
            result.put("status", true);
            result.put("message", "添加车辆成功");
        } catch (Exception e) {
            logger.error("添加车辆失败", e);
            result.put("status", false);
            result.put("message", "添加车辆失败");
        }
        return result;
    }

    // 修改车辆
    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody CarInfo carInfo) {
        Map<String, Object> result = new HashMap<>();
        try {
            carInfoService.update(carInfo);
            result.put("status", true);
            result.put("message", "修改车辆成功");
        } catch (Exception e) {
            logger.error("修改车辆失败", e);
            result.put("status", false);
            result.put("message", "修改车辆失败");
        }
        return result;
    }

    // 删除车辆
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            carInfoService.delete(id);
            result.put("status", true);
            result.put("message", "删除车辆成功");
        } catch (Exception e) {
            logger.error("删除车辆失败", e);
            result.put("status", false);
            result.put("message", "删除车辆失败");
        }
        return result;
    }

    // 批量删除设备
    @PostMapping("/deleteBatch")
    public Map<String, Object> deleteBatch(@RequestBody List<Long> ids) {
        Map<String, Object> result = new HashMap<>();
        try {
            carInfoService.deleteBatch(ids);
            result.put("status", true);
            result.put("message", "批量删除车辆成功");
        } catch (Exception e) {
            logger.error("批量删除设备失败", e);
            result.put("status", false);
            result.put("message", "批量删除车辆失败");
        }
        return result;
    }

}
