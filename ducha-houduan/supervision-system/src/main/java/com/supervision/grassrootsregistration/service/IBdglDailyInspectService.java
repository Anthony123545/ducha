package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglDailyInspect;

/**
 * 查铺查哨Service接口
 * 
 * @author supervision
 * @date 2022-02-28
 */
public interface IBdglDailyInspectService 
{
    /**
     * 查询查铺查哨
     * 
     * @param id 查铺查哨主键
     * @return 查铺查哨
     */
    public BdglDailyInspect selectBdglDailyInspectById(Integer id);

    /**
     * 查询查铺查哨列表
     * 
     * @param bdglDailyInspect 查铺查哨
     * @return 查铺查哨集合
     */
    public List<BdglDailyInspect> selectBdglDailyInspectList(BdglDailyInspect bdglDailyInspect);

    /**
     * 新增查铺查哨
     * 
     * @param bdglDailyInspect 查铺查哨
     * @return 结果
     */
    public int insertBdglDailyInspect(BdglDailyInspect bdglDailyInspect);

    /**
     * 修改查铺查哨
     * 
     * @param bdglDailyInspect 查铺查哨
     * @return 结果
     */
    public int updateBdglDailyInspect(BdglDailyInspect bdglDailyInspect);

    /**
     * 批量删除查铺查哨
     * 
     * @param ids 需要删除的查铺查哨主键集合
     * @return 结果
     */
    public int deleteBdglDailyInspectByIds(Integer[] ids);

    /**
     * 删除查铺查哨信息
     * 
     * @param id 查铺查哨主键
     * @return 结果
     */
    public int deleteBdglDailyInspectById(Integer id);

    /**
     * 查询当前时间、当前部门下是否有数据
     * @param starttime
     * @param endtime
     * @param unitid
     * @return
     */
    public List<BdglDailyInspect> selectBdglDailyInspectListByTime(String starttime, String endtime, Long unitid);

    List<BdglDailyInspect> selectBdglDailyInspects(Long deptId, String timeParameter);
    
}
