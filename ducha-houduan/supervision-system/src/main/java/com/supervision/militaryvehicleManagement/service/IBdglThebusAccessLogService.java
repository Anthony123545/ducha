package com.supervision.militaryvehicleManagement.service;

import java.util.List;
import com.supervision.militaryvehicleManagement.domain.BdglThebusAccessLog;

/**
 * 军车进出查询Service接口
 * 
 * @author supervision
 * @date 2022-03-09
 */
public interface IBdglThebusAccessLogService 
{
    /**
     * 查询军车进出查询
     * 
     * @param id 军车进出查询主键
     * @return 军车进出查询
     */
    public BdglThebusAccessLog selectBdglThebusAccessLogById(Integer id);

    /**
     * 查询军车进出查询列表
     * 
     * @param bdglThebusAccessLog 军车进出查询
     * @return 军车进出查询集合
     */
    public List<BdglThebusAccessLog> selectBdglThebusAccessLogList(BdglThebusAccessLog bdglThebusAccessLog);

    /**
     * 新增军车进出查询
     * 
     * @param bdglThebusAccessLog 军车进出查询
     * @return 结果
     */
    public int insertBdglThebusAccessLog(BdglThebusAccessLog bdglThebusAccessLog);

    /**
     * 修改军车进出查询
     * 
     * @param bdglThebusAccessLog 军车进出查询
     * @return 结果
     */
    public int updateBdglThebusAccessLog(BdglThebusAccessLog bdglThebusAccessLog);

    /**
     * 批量删除军车进出查询
     * 
     * @param ids 需要删除的军车进出查询主键集合
     * @return 结果
     */
    public int deleteBdglThebusAccessLogByIds(Integer[] ids);

    /**
     * 删除军车进出查询信息
     * 
     * @param id 军车进出查询主键
     * @return 结果
     */
    public int deleteBdglThebusAccessLogById(Integer id);

    List<BdglThebusAccessLog> selectBdglThebusAccessLogLists(BdglThebusAccessLog bdglThebusAccessLog);
}
