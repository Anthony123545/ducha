package com.supervision.web.Hikvision.peopleDoor.web.controller;


import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/device")
public class DeviceManageController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceManageController.class);

    @Autowired
    private DeviceService deviceService;

    /**
     * 查看单个设备详情
     */
    @GetMapping("/get/{id}")
    public Map<String, Object> getDevice(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Device device = deviceService.getById(id);
            result.put("data", device);
            result.put("status", true);
            result.put("message", "获取单个设备详情成功");
        } catch (Exception e) {
            logger.error("获取单个设备详情失败", e);
            result.put("status", false);
            result.put("message", "获取单个设备详情失败");
        }
        return result;
    }

    /**
     * 获取所有设备列表
     */
    @PostMapping("/list")
    public Map<String, Object> list(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            Object data = deviceService.searchByCondition(params);

            if (data instanceof PageInfo) {
                PageInfo<Device> pageInfo = (PageInfo<Device>) data;
                result.put("total", pageInfo.getTotal());
                result.put("list", pageInfo.getList());
                result.put("pageNum", pageInfo.getPageNum());
                result.put("pageSize", pageInfo.getPageSize());
            } else if (data instanceof List) {
                List<Device> list = (List<Device>) data;
                result.put("total", list.size());
                result.put("list", list);
                result.put("pageNum", 1);
                result.put("pageSize", list.size());
            }

            result.put("status", true);
            result.put("message", "获取设备信息成功");
        } catch (Exception e) {
            logger.error("获取设备信息失败", e);
            result.put("status", false);
            result.put("message", "获取设备信息失败");
        }
        return result;
    }

    /**
     * 获取所有设备列表
     */
    @GetMapping("/listDeviceName")
    public Map<String, Object> listDeviceName() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Device> pageInfo = deviceService.searchAllDeviceNames();
            result.put("total", pageInfo.size());
            result.put("list", pageInfo);
            result.put("status", true);
            result.put("message", "获取所有设备列表成功");
        } catch (Exception e) {
            logger.error("获取所有设备列表失败", e);
            result.put("status", false);
            result.put("message", "获取所有设备列表失败");
        }
        return result;
    }

    /**
     * 新增设备
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody @Valid Device device) {
        Map<String, Object> result = new HashMap<>();
        try {
            deviceService.add(device);  // 保存到数据库
            result.put("status", true);
            result.put("message", "添加设备成功");
        } catch (Exception e) {
            logger.error("添加设备失败", e);
            result.put("status", false);
            result.put("message", "添加设备失败");
        }
        return result;
    }

    /**
     * 修改设备
     */
    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Device device) {
        Map<String, Object> result = new HashMap<>();
        try {
            deviceService.update(device);
            result.put("status", true);
            result.put("message", "修改设备成功");
        } catch (Exception e) {
            logger.error("修改设备失败", e);
            result.put("status", false);
            result.put("message", "修改设备失败");
        }
        return result;
    }

    // 单条删除设备
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            deviceService.delete(id);
            result.put("status", true);
            result.put("message", "删除设备成功");
        } catch (Exception e) {
            logger.error("删除设备失败", e);
            result.put("status", false);
            result.put("message", "删除设备失败");
        }
        return result;
    }

    // 批量删除设备
    @PostMapping("/deleteBatch")
    public Map<String, Object> deleteBatch(@RequestBody List<Long> ids) {
        Map<String, Object> result = new HashMap<>();
        try {
            deviceService.deleteBatch(ids);
            result.put("status", true);
            result.put("message", "批量删除设备成功");
        } catch (Exception e) {
            logger.error("批量删除设备失败", e);
            result.put("status", false);
            result.put("message", "批量删除设备失败");
        }
        return result;
    }

}

