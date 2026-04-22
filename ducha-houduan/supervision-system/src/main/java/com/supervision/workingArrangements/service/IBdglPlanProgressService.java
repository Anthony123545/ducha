package com.supervision.workingArrangements.service;

import java.util.List;
import com.supervision.workingArrangements.domain.BdglPlanProgress;

/**
 * 进度录入Service接口
 * 
 * @author supervision
 * @date 2022-03-10
 */
public interface IBdglPlanProgressService 
{
    /**
     * 查询进度录入
     * 
     * @param id 进度录入主键
     * @return 进度录入
     */
    public BdglPlanProgress selectBdglPlanProgressById(Integer id);

    /**
     * 查询进度录入列表
     * 
     * @param bdglPlanProgress 进度录入
     * @return 进度录入集合
     */
    public List<BdglPlanProgress> selectBdglPlanProgressList(BdglPlanProgress bdglPlanProgress);

    /**
     * 新增进度录入
     * 
     * @param bdglPlanProgress 进度录入
     * @return 结果
     */
    public int insertBdglPlanProgress(BdglPlanProgress bdglPlanProgress);

    /**
     * 修改进度录入
     * 
     * @param bdglPlanProgress 进度录入
     * @return 结果
     */
    public int updateBdglPlanProgress(BdglPlanProgress bdglPlanProgress);

    /**
     * 批量删除进度录入
     * 
     * @param ids 需要删除的进度录入主键集合
     * @return 结果
     */
    public int deleteBdglPlanProgressByIds(Integer[] ids);

    /**
     * 删除进度录入信息
     * 
     * @param id 进度录入主键
     * @return 结果
     */
    public int deleteBdglPlanProgressById(Integer id);


    BdglPlanProgress select(BdglPlanProgress bdglPlanProgress1);

}
