package com.supervision.workingArrangements.service;

import java.util.List;
import com.supervision.workingArrangements.domain.BdglWorkProgress;

/**
 * 工作任务进度录入Service接口
 * 
 * @author supervision
 * @date 2022-03-10
 */
public interface IBdglWorkProgressService 
{
    /**
     * 查询工作任务进度录入
     * 
     * @param id 工作任务进度录入主键
     * @return 工作任务进度录入
     */
    public BdglWorkProgress selectBdglWorkProgressById(Integer id);

    /**
     * 查询工作任务进度录入列表
     * 
     * @param bdglWorkProgress 工作任务进度录入
     * @return 工作任务进度录入集合
     */
    public List<BdglWorkProgress> selectBdglWorkProgressList(BdglWorkProgress bdglWorkProgress);

    /**
     * 新增工作任务进度录入
     * 
     * @param bdglWorkProgress 工作任务进度录入
     * @return 结果
     */
    public int insertBdglWorkProgress(BdglWorkProgress bdglWorkProgress);

    /**
     * 修改工作任务进度录入
     * 
     * @param bdglWorkProgress 工作任务进度录入
     * @return 结果
     */
    public int updateBdglWorkProgress(BdglWorkProgress bdglWorkProgress);

    /**
     * 批量删除工作任务进度录入
     * 
     * @param ids 需要删除的工作任务进度录入主键集合
     * @return 结果
     */
    public int deleteBdglWorkProgressByIds(Integer[] ids);

    /**
     * 删除工作任务进度录入信息
     * 
     * @param id 工作任务进度录入主键
     * @return 结果
     */
    public int deleteBdglWorkProgressById(Integer id);

    BdglWorkProgress selectBdglWorkProgressBYTaskId(BdglWorkProgress workProgress);

    List<BdglWorkProgress> selectBdglWorkProgressLists(String[] split);
}
