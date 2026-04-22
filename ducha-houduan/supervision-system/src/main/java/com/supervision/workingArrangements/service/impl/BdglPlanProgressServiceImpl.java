package com.supervision.workingArrangements.service.impl;

import java.util.List;

import com.supervision.workingArrangements.domain.BdglPlanTask;
import com.supervision.workingArrangements.mapper.BdglPlanTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.workingArrangements.mapper.BdglPlanProgressMapper;
import com.supervision.workingArrangements.domain.BdglPlanProgress;
import com.supervision.workingArrangements.service.IBdglPlanProgressService;

/**
 * 进度录入Service业务层处理
 *
 * @author supervision
 * @date 2022-03-10
 */
@Service
public class BdglPlanProgressServiceImpl implements IBdglPlanProgressService
{
    @Autowired
    private BdglPlanProgressMapper bdglPlanProgressMapper;
    @Autowired
    private BdglPlanTaskMapper planTaskMapper;

    /**
     * 查询进度录入
     *
     * @param id 进度录入主键
     * @return 进度录入
     */
    @Override
    public BdglPlanProgress selectBdglPlanProgressById(Integer id)
    {
        return bdglPlanProgressMapper.selectBdglPlanProgressById(id);
    }

    /**
     * 查询进度录入列表
     *
     * @param bdglPlanProgress 进度录入
     * @return 进度录入
     */
    @Override
    public List<BdglPlanProgress> selectBdglPlanProgressList(BdglPlanProgress bdglPlanProgress)
    {
        return bdglPlanProgressMapper.selectBdglPlanProgressList(bdglPlanProgress);
    }

    /**
     * 新增进度录入
     *
     * @param bdglPlanProgress 进度录入
     * @return 结果
     */
    @Override
    public int insertBdglPlanProgress(BdglPlanProgress bdglPlanProgress)
    {
        return bdglPlanProgressMapper.insertBdglPlanProgress(bdglPlanProgress);
    }

    /**
     * 修改进度录入
     *
     * @param bdglPlanProgress 进度录入
     * @return 结果
     */
    @Override
    public int updateBdglPlanProgress(BdglPlanProgress bdglPlanProgress)
    {

        if(bdglPlanProgress.getSchedule()==100){
            bdglPlanProgress.setStauts(1);
        }

        return bdglPlanProgressMapper.updateBdglPlanProgress(bdglPlanProgress);
    }

    /**
     * 批量删除进度录入
     *
     * @param ids 需要删除的进度录入主键
     * @return 结果
     */
    @Override
    public int deleteBdglPlanProgressByIds(Integer[] ids)
    {
        return bdglPlanProgressMapper.deleteBdglPlanProgressByIds(ids);
    }

    /**
     * 删除进度录入信息
     *
     * @param id 进度录入主键
     * @return 结果
     */
    @Override
    public int deleteBdglPlanProgressById(Integer id)
    {
        return bdglPlanProgressMapper.deleteBdglPlanProgressById(id);
    }

    @Override
    public BdglPlanProgress select(BdglPlanProgress bdglPlanProgress1) {
        return bdglPlanProgressMapper.select(bdglPlanProgress1);
    }
}
