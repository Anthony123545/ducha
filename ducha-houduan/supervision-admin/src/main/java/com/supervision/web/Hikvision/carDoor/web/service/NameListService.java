package com.supervision.web.Hikvision.carDoor.web.service;

import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.carDoor.web.entity.NameList;

import java.util.List;
import java.util.Map;

/**
 * 车辆名单服务接口
 * 提供对车辆授权/非授权名单的增删改查操作，
 * 并支持与海康门禁设备的同步操作。
 */
public interface NameListService {

    /**
     * 查询指定设备的名单列表（分页）
     *
     * @param params    查询条件
     * @return 名单列表
     */
    PageInfo<NameList> searchByCondition(Map<String, Object> params);

    /**
     * 新增名单并同步到设备
     *
     * @param nameList 名单信息
     * @return 操作是否成功
     */
    int add(NameList nameList);

    /**
     * 更新名单信息并同步到设备
     *
     * @param nameList 名单信息
     * @return 操作是否成功
     */
    int update(NameList nameList);

    /**
     * 删除单条名单并同步删除设备
     *
     * @param id 名单ID
     * @return 操作是否成功
     */
    int delete(Long id);

    /**
     * 批量删除名单并同步删除设备
     *
     * @param ids 名单ID列表
     * @return 操作是否成功
     */
    int batchDelete(List<Long> ids);

    /**
     * 判断某车牌在指定设备和类型下是否存在
     *
     * @param deviceId    设备ID
     * @param licensePlate 车牌号
     * @param type        名单类型
     * @return 是否存在
     */
    boolean exists(Long deviceId, Integer type, String licensePlate);

    /**
     * 判断某车牌在指定设备和类型下是否存在（排除指定ID，用于更新时校验重复）
     *
     * @param deviceId    设备ID
     * @param licensePlate 车牌号
     * @param type        名单类型
     * @param id          排除的ID
     * @return 是否存在
     */
    boolean existsExcludeId(Long deviceId, Integer type, String licensePlate, Long id);

//    /**
//     * 同步单条名单到设备
//     *
//     * @param nameList 名单信息
//     * @return 同步是否成功
//     * @throws Exception 设备同步异常
//     */
//    boolean syncToDevice(NameList nameList) throws Exception;
//
//    /**
//     * 删除设备中的单条名单
//     *
//     * @param nameList 名单信息
//     * @return 删除是否成功
//     */
//    boolean deleteFromDevice(NameList nameList);
//
//    /**
//     * 根据设备ID获取 SDK 登录返回的 UserID
//     *
//     * @param deviceId 设备ID
//     * @return SDK 用户登录句柄 UserID
//     */
//    int getDeviceUserID(Long deviceId);
}
