package com.supervision.workingArrangements.mapper;

import java.util.List;
import com.supervision.workingArrangements.domain.BdglWorkProgress;
import org.apache.ibatis.annotations.Param;

/**
 * 工作任务进度录入Mapper接口
 * 
 * @author supervision
 * @date 2022-03-10
 */
public interface BdglWorkProgressMapper 
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
     * 删除工作任务进度录入
     * 
     * @param id 工作任务进度录入主键
     * @return 结果
     */
    public int deleteBdglWorkProgressById(Integer id);

    /**
     * 批量删除工作任务进度录入
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglWorkProgressByIds(Integer[] ids);

    BdglWorkProgress selectBdglWorkProgressBYTaskId(BdglWorkProgress workProgress);

    List<BdglWorkProgress> selectBdglWorkProgressLists(@Param("split") String[] split);

    void deleteBdglWorkProgress(Integer id);

    int deleteBdglWorkProgressByTaskId(BdglWorkProgress bdglWorkProgress);
}
