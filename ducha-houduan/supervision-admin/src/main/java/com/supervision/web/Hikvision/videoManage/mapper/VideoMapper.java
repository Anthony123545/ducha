package com.supervision.web.Hikvision.videoManage.mapper;


import com.supervision.web.Hikvision.videoManage.domain.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 视频设备数据访问层
 * 对应表：new_video_info
 */
@Mapper
public interface VideoMapper {

    /** 按条件查询（分页） */
    List<Video> searchByCondition(@Param("name") String name,
                                  @Param("deviceType") String deviceType,
                                  @Param("enable") Boolean enable
    );

    /** 按名称模糊查询 */
    List<Video> searchByName(@Param("name") String name);

    /** 按父设备ID查询（多通道/门禁子视频） */
    List<Video> searchByParentDeviceId(@Param("parentDeviceId") Long parentDeviceId);

    /** 按ID查询视频 */
    Video searchById(@Param("id") Long id);

    /** 按IP查询视频 */
    Video searchByIp(@Param("ip") String ip);

    /** 判断IP地址是否已存在 */
    boolean existsByIp(@Param("ip") String ip);

    /** 新增视频记录 */
    int insert(Video video);

    /** 更新视频记录 */
    int update(Video video);

    int setParentDeviceId(@Param("id") Long id, @Param("parentDeviceId") Long parentDeviceId);

    int unsetParentDeviceIdByDeviceId(@Param("parentDeviceId") Long parentDeviceId);

    int unsetParentDeviceIds(@Param("parentDeviceIds") List<Long> parentDeviceIds);

    /** 删除视频记录 */
    int deleteById(@Param("id") Long id);

    /** 批量删除视频记录 */
    int batchDelete(@Param("ids") List<Long> ids);

    void batchInsert(List<Video> videos);

}
