package com.supervision.web.Hikvision.peopleDoor.web.service.impl;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.mapper.DeviceMapper;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import com.supervision.web.Hikvision.videoManage.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * DeviceServiceImpl
 * 海康设备管理服务实现
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public Object searchByCondition(Map<String, Object> params){
        String deviceName = (String) params.getOrDefault("deviceName", null);
        String deviceType = (String) params.getOrDefault("deviceType", null);
        Boolean enable = (Boolean) params.getOrDefault("enable", null);

        // 是否分页，默认分页
        boolean paginate = (boolean) params.getOrDefault("paginate", true);
        int page = (int) params.getOrDefault("page", 1);
        int size = (int) params.getOrDefault("size", 10);

        if (paginate) {
            PageHelper.startPage(page, size);
            List<Device> list = deviceMapper.searchByCondition(deviceName, deviceType, enable);
            return new PageInfo<>(list); // 分页结果
        } else {
            List<Device> list = deviceMapper.searchByCondition(deviceName, deviceType, enable);
            return list; // 全部结果
        }
    };

    /**
     * 查询所有设备名称（下拉框用）
     *
     * @return 设备列表，仅包含 id 和 name
     */
    @Override
    public List<Device> searchAllDeviceNames() {
        return deviceMapper.searchAllDeviceNames();
    }

    /**
     * 根据ID查询设备
     *
     * @param id 设备ID
     * @return 设备实体
     */
    @Override
    public Device getById(Long id) {
        return deviceMapper.searchById(id);
    }

    /**
     * 根据IP查询设备
     *
     * @param ip 设备IP
     * @return 设备实体
     */
    @Override
    public Device getByIp(String ip) {
        return deviceMapper.searchByIp(ip);
    }

    /**
     * 插入设备
     *
     * @param device 设备实体
     * @return 插入成功后的ID
     */
    @Override
    public Long add(Device device) {
        deviceMapper.insert(device);
        // 获取插入后的 Device ID
        Long deviceId = device.getId();
        if (deviceId == null) {
            logger.error("插入 Device 后未能获取到 ID");
            return null; // 或者根据需求抛出异常
        }
        // 使用 Fastjson2 解析 JSON 字符串为 JSONArray
        JSONArray jsonArray;
        try {
            jsonArray = com.alibaba.fastjson2.JSON.parseArray(device.getVideosJson());
        } catch (Exception e) {
            logger.error("解析 videosJson 错误", e);
            return deviceId;
        }
        // 遍历每个摄像头对象
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject cameraObject = jsonArray.getJSONObject(i);

            // 提取 videoId，假设 videoId 是整数类型
            Integer videoIdInteger = cameraObject.getInteger("videoId");
            if (videoIdInteger == null) {
                logger.warn("第 {} 个摄像头缺少 videoId", i + 1);
                continue;
            }
            Long videoId = Long.valueOf(videoIdInteger);
            // 更新 Video 的 parentDeviceId 为当前 Device 的 ID
            int res = videoMapper.setParentDeviceId(videoId, deviceId);
            if (res == 0) {
                logger.error("更新视频设备ID失败，videoId: {}", videoId);
            } else {
                logger.debug("成功更新视频设备ID，videoId: {}, deviceId: {}", videoId, deviceId);
            }
        }

        // 返回插入后的 Device ID
        return deviceId;
    }

    /**
     * 更新设备信息
     *
     * @param device 设备实体
     * @return 影响行数
     */
    @Override
    @Transactional // 确保更新 Device 和更新 Video 的操作在一个事务中
    public int update(Device device) {
        // 更新 Device 信息
        int affectedRows = deviceMapper.update(device);
        if (affectedRows == 0) {
            logger.warn("更新 Device 失败，可能设备不存在，device: {}", device);
            return affectedRows;
        }

        // 获取更新后的 Device ID
        Long deviceId = device.getId();
        if (deviceId == null) {
            logger.error("更新 Device 后未能获取到 ID");
            return affectedRows;
        }

        // 检查是否存在 videosJson 字段
        String videosJson = device.getVideosJson();
        if (videosJson == null || videosJson.isEmpty()) {
            logger.debug("更新 Device 时，videosJson 为空或未提供，跳过更新 Video 的 parentDeviceId");
            return affectedRows;
        }

        // 使用 Fastjson2 解析 JSON 字符串为 JSONArray
        JSONArray jsonArray;
        try {
            jsonArray = com.alibaba.fastjson2.JSON.parseArray(videosJson);
        } catch (Exception e) {
            logger.error("解析 videosJson 错误", e);
            return affectedRows;
        }

        // 遍历每个摄像头对象
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject cameraObject = jsonArray.getJSONObject(i);

            // 提取 videoId，假设 videoId 是整数类型
            Integer videoIdInteger = cameraObject.getInteger("videoId");
            if (videoIdInteger == null) {
                logger.warn("第 {} 个摄像头缺少 videoId", i + 1);
                continue;
            }
            Long videoId = Long.valueOf(videoIdInteger);

            // 更新 Video 的 parentDeviceId 为当前 Device 的 ID
            int res = videoMapper.setParentDeviceId(videoId, deviceId);
            if (res == 0) {
                logger.error("更新视频设备ID失败，videoId: {}", videoId);
            } else {
                logger.debug("成功更新视频设备ID，videoId: {}, deviceId: {}", videoId, deviceId);
            }
        }

        // 返回影响的行数
        return affectedRows;
    }

    /**
     * 删除设备
     *
     * @param id 设备ID
     * @return 影响行数（删除设备的行数）
     */
    @Override
    @Transactional // 确保更新 Video 和删除 Device 的操作在一个事务中
    public int delete(Long id) {
        // 1. 更新所有绑定到该设备的视频的 parentDeviceId 为 null
        int updatedVideosCount = videoMapper.unsetParentDeviceIdByDeviceId(id);

        if (updatedVideosCount < 0) {
            logger.error("解除视频与设备关联时发生错误，设备ID: {}", id);
            // 根据业务需求，您可以选择抛出异常或继续执行删除操作
            // 这里选择继续执行删除操作，但您可以根据需要调整
        } else {
            logger.debug("成功解除 {} 个视频与设备ID {} 的关联", updatedVideosCount, id);
        }

        // 2. 删除设备
        int deletedDeviceCount = deviceMapper.delete(id);

        if (deletedDeviceCount == 0) {
            logger.warn("删除设备失败，可能设备不存在，设备ID: {}", id);
            // 根据业务需求，您可以选择抛出异常或返回影响行数
        } else {
            logger.debug("成功删除设备，设备ID: {}", id);
        }

        // 返回删除设备的行数
        return deletedDeviceCount;
    }

    /**
     * 批量删除设备
     *
     * @param ids 设备ID列表
     * @return 影响行数（成功删除的设备数量）
     */
    @Override
    @Transactional
    public int deleteBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            logger.warn("批量删除设备：设备ID列表为空");
            return 0;
        }

        // 1. 批量解除所有绑定到这些设备的视频的 parentDeviceId
        int updatedVideosCount = videoMapper.unsetParentDeviceIds(ids);

        if (updatedVideosCount < 0) {
            logger.error("批量解除视频关联失败，设备ID列表: {}", ids);
            // 可以选择抛出异常或继续执行
        } else {
            logger.debug("成功解除 {} 个视频与设备ID列表 {} 的关联", updatedVideosCount, ids);
        }

        // 2. 批量删除设备
        int deletedDeviceCount = deviceMapper.deleteBatch(ids);

        if (deletedDeviceCount < 0) {
            logger.error("批量删除设备失败");
            throw new RuntimeException("批量删除设备失败");
        } else if (deletedDeviceCount == 0) {
            logger.warn("批量删除设备：没有设备被删除（可能ID都不存在）");
        } else {
            logger.debug("成功批量删除 {} 个设备", deletedDeviceCount);
        }

        return deletedDeviceCount;
    }

    @Override
    public List<Device> list() {
        return deviceMapper.searchByCondition( null, null, null);
    }

}