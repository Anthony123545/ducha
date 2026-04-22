package com.supervision.barracksManagement.service;

import java.util.List;
import com.supervision.barracksManagement.domain.BdglRepair;

/**
 * 维修申请Service接口
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public interface IBdglRepairService 
{
    /**
     * 查询维修申请
     * 
     * @param id 维修申请主键
     * @return 维修申请
     */
    public BdglRepair selectBdglRepairById(Long id);

    /**
     * 查询维修申请列表
     * 
     * @param bdglRepair 维修申请
     * @return 维修申请集合
     */
    public List<BdglRepair> selectBdglRepairList(BdglRepair bdglRepair);

    /**
     * 新增维修申请
     * 
     * @param bdglRepair 维修申请
     * @return 结果
     */
    public int insertBdglRepair(BdglRepair bdglRepair);

    /**
     * 修改维修申请
     * 
     * @param bdglRepair 维修申请
     * @return 结果
     */
    public int updateBdglRepair(BdglRepair bdglRepair);

    /**
     * 批量删除维修申请
     * 
     * @param ids 需要删除的维修申请主键集合
     * @return 结果
     */
    public int deleteBdglRepairByIds(Long[] ids);

    /**
     * 删除维修申请信息
     * 
     * @param id 维修申请主键
     * @return 结果
     */
    public int deleteBdglRepairById(Long id);
}
