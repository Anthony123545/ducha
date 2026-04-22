package com.supervision.web.Hikvision.peopleDoor.web.mapper;


import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DeviceMapper
 * 海康设备信息的 MyBatis Mapper 接口
 * 提供设备的增删改查以及条件查询方法
 */
@Mapper
public interface DeviceMapper {

    /**
     * 按条件查询设备（可模糊匹配设备名称）
     * @param name 设备名称，可为空
     * @return 符合条件的设备列表
     */
    List<Device> searchByCondition(
            @Param("name") String name,
            @Param("type") String type,
            @Param("enable") Boolean enable
    );

    /**
     * 查询所有设备名称（用于下拉框等）
     * 仅返回 id 和 name
     * @return 设备列表
     */
    List<Device> searchAllDeviceNames();

    /**
     * 根据设备ID查询设备信息
     * @param id 设备ID
     * @return 设备实体
     */
    Device searchById(@Param("id") Long id);

    /**
     * 根据设备IP查询设备信息
     * @param ip 设备IP
     * @return 设备实体
     */
    Device searchByIp(@Param("ip") String ip);

    /**
     * 插入设备信息
     * @param device 设备实体
     * @return 影响行数
     */
    int insert(Device device);

    /**
     * 更新设备信息
     * @param device 设备实体
     * @return 影响行数
     */
    int update(Device device);

    /**
     * 删除设备信息
     * @param id 设备ID
     * @return 影响行数
     */
    int delete(@Param("id") Long id);

    /**
     * 批量删除设备信息
     * @param ids 设备ID列表
     * @return 影响行数
     */
    int deleteBatch(@Param("ids") List<Long> ids);

}


