package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration .domain.BdglSoldier;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;

/**
 * 军人委员会会议登记本Service接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface IBdglSoldierService 
{
    /**
     * 查询军人委员会会议登记本
     * 
     * @param id 军人委员会会议登记本主键
     * @return 军人委员会会议登记本
     */
    public BdglSoldier selectBdglSoldierById(Integer id);

    /**
     * 查询军人委员会会议登记本列表
     * 
     * @param bdglSoldier 军人委员会会议登记本
     * @return 军人委员会会议登记本集合
     */
    public List<BdglSoldier> selectBdglSoldierList(BdglSoldier bdglSoldier);

    /**
     * 新增军人委员会会议登记本
     * 
     * @param bdglSoldier 军人委员会会议登记本
     * @return 结果
     */
    public int insertBdglSoldier(BdglSoldier bdglSoldier);

    /**
     * 修改军人委员会会议登记本
     * 
     * @param bdglSoldier 军人委员会会议登记本
     * @return 结果
     */
    public int updateBdglSoldier(BdglSoldier bdglSoldier);

    /**
     * 批量删除军人委员会会议登记本
     * 
     * @param ids 需要删除的军人委员会会议登记本主键集合
     * @return 结果
     */
    public int deleteBdglSoldierByIds(Integer[] ids);

    /**
     * 删除军人委员会会议登记本信息
     * 
     * @param id 军人委员会会议登记本主键
     * @return 结果
     */
    public int deleteBdglSoldierById(Integer id);
    
    List<SysDeptDto> querySoldierList(String year);
    
    List<BdglSoldier> getSoldierOnUnitList(Integer unitId,String year);
}
