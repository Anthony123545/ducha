package com.supervision.web.Hikvision.videoManage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.videoManage.domain.Video;
import com.supervision.web.Hikvision.videoManage.mapper.VideoMapper;
import com.supervision.web.Hikvision.videoManage.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service("VideoService")
public class VideoServiceImpl implements VideoService {

    /** 注入 Mapper */
    @Autowired
    private VideoMapper videoMapper;

    /** 条件分页查询视频 */
    @Override
    public Object searchByCondition(Map<String, Object> params) {
        String name = (String) params.getOrDefault("name", null);
        Boolean enable = (Boolean) params.getOrDefault("enable", null);

        Object deviceTypeObj = params.get("deviceType");  // 不用 getOrDefault，直接 get 也可以
        String deviceType = null;

        if (deviceTypeObj != null) {
            if (deviceTypeObj instanceof String) {
                deviceType = (String) deviceTypeObj;
            } else if (deviceTypeObj instanceof Integer) {
                // 如果后端传过来的是数字，比如 1 表示 Android，2 表示 iOS，你可以转为字符串，或者转为枚举
                deviceType = String.valueOf(deviceTypeObj); // "1", "2"...
                // 或者 int deviceTypeInt = (Integer) deviceTypeObj; 然后做业务逻辑判断
            } else {
                log.warn("deviceType 的类型不是预期的 String 或 Integer，实际类型：{}", deviceTypeObj.getClass());
            }
        }

        // 是否分页，默认分页
        boolean paginate = (boolean) params.getOrDefault("paginate", true);
        int page = (int) params.getOrDefault("page", 1);
        int size = (int) params.getOrDefault("size", 10);

        if (paginate) {
            PageHelper.startPage(page, size);
            List<Video> list = videoMapper.searchByCondition(name, deviceType, enable);
            return new PageInfo<>(list); // 分页结果
        } else {
            List<Video> list = videoMapper.searchByCondition(name, deviceType, enable);
            return list; // 全部结果
        }
    }

    /** 根据名称模糊查询 */
    @Override
    public List<Video> listByName(String name) {
        return videoMapper.searchByName(name);
    }

    /** 根据父设备ID查询视频 */
    @Override
    public List<Video> listByParentDeviceId(Long parentDeviceId) {
        return videoMapper.searchByParentDeviceId(parentDeviceId);
    }

    /** 根据ID查询视频 */
    @Override
    public Video getById(Long id) {
        return videoMapper.searchById(id);
    }

    /** 根据IP查询视频 */
    @Override
    public Video getByIp(String ip) {
        return videoMapper.searchByIp(ip);
    }

    /**
     * 判断IP是否存在
     */
    @Override
    public boolean existsByIp(String ip) {
        return videoMapper.existsByIp(ip);
    }

    /** 新增视频 */
    @Override
    public int add(Video video) {
        return videoMapper.insert(video);
    }

    /** 更新视频 */
    @Override
    public int update(Video video) {
        return videoMapper.update(video);
    }

    /** 删除视频 */
    @Override
    public int delete(Long id) {
        return videoMapper.deleteById(id);
    }

    /** 批量删除视频 */
    @Override
    public int batchDelete(List<Long> ids) {
        return videoMapper.batchDelete(ids);
    }

    /** 批量新增视频 */
    @Override
    public void batchAdd(List<Video> videos) {
        videoMapper.batchInsert(videos);
    }
}
