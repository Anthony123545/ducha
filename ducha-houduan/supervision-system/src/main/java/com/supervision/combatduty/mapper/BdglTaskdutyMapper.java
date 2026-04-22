package com.supervision.combatduty.mapper;

import java.util.List;
import com.supervision.combatduty.domain.BdglTaskduty;

/**
 * 执行任务Mapper接口
 * 
 * @author supervision
 * @date 2022-03-11
 */
public interface BdglTaskdutyMapper 
{
    /**
     * 查询执行任务
     * 
     * @param id 执行任务主键
     * @return 执行任务
     */
    public BdglTaskduty selectBdglTaskdutyById(Long id);

    /**
     * 查询执行任务列表
     * 
     * @param bdglTaskduty 执行任务
     * @return 执行任务集合
     */
    public List<BdglTaskduty> selectBdglTaskdutyList(BdglTaskduty bdglTaskduty);

    /**
     * 新增执行任务
     * 
     * @param bdglTaskduty 执行任务
     * @return 结果
     */
    public int insertBdglTaskduty(BdglTaskduty bdglTaskduty);

    /**
     * 修改执行任务
     * 
     * @param bdglTaskduty 执行任务
     * @return 结果
     */
    public int updateBdglTaskduty(BdglTaskduty bdglTaskduty);

    /**
     * 删除执行任务
     * 
     * @param id 执行任务主键
     * @return 结果
     */
    public int deleteBdglTaskdutyById(Long id);

    /**
     * 批量删除执行任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglTaskdutyByIds(Long[] ids);
}
