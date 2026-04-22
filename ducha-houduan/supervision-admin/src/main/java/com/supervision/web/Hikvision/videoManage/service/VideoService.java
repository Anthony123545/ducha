package com.supervision.web.Hikvision.videoManage.service;

import com.supervision.web.Hikvision.videoManage.domain.Video;

import java.util.List;
import java.util.Map;

/**
 * 视频管理服务接口层
 */
public interface VideoService {

    /** 按条件分页查询视频 */
    Object searchByCondition(Map<String, Object> params);

    /** 根据名称模糊查询视频 */
    List<Video> listByName(String name);

    /** 根据父设备ID查询视频 */
    List<Video> listByParentDeviceId(Long parentDeviceId);

    /** 根据ID查询视频 */
    Video getById(Long id);

    /** 根据IP查询视频 */
    Video getByIp(String ip);

    /* 判断IP是否存在 */
    boolean existsByIp(String ip);

    /** 新增视频 */
    int add(Video video);

    /** 更新视频 */
    int update(Video video);

    /** 删除视频 */
    int delete(Long id);

    /** 批量删除视频 */
    int batchDelete(List<Long> ids);

    void batchAdd(List<Video> videos);

}
