package com.supervision.web.Hikvision.peopleDoor.web.service;


import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;

import java.util.List;
import java.util.Map;

/**
 * DeviceService
 * 海康设备管理服务接口
 */
public interface DeviceService {

    Object searchByCondition(Map<String, Object> params);

    /**
     * 查询所有设备名称（下拉框用，仅返回 id 和 name）
     *
     * @return 设备列表
     */
    List<Device> searchAllDeviceNames();

    /**
     * 根据ID查询设备
     *
     * @param id 设备ID
     * @return 设备实体
     */
    Device getById(Long id);

    /**
     * 根据ID查询设备
     *
     * @param ip 设备IP
     * @return 设备实体
     */
    Device getByIp(String ip);

    /**
     * 插入设备
     *
     * @param device 设备实体
     * @return 插入后的ID
     */
    Long add(Device device);

    /**
     * 更新设备信息
     *
     * @param device 设备实体
     * @return 影响行数
     */
    int update(Device device);

    /**
     * 删除设备
     *
     * @param id 设备ID
     * @return 影响行数
     */
    int delete(Long id);

    /**
     * 批量删除设备
     *
     * @param ids 设备ID列表
     * @return 影响行数
     */
    int deleteBatch(List<Long> ids);

    List<Device> list();
}
