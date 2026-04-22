package com.supervision.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.system.mapper.MilitaryTrainingMapper;
import com.supervision.system.domain.MilitaryTraining;
import com.supervision.system.service.IMilitaryTrainingService;

/**
 * 态势军事训练月统计情况Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-05-21
 */
@Service
public class MilitaryTrainingServiceImpl implements IMilitaryTrainingService 
{
    @Autowired
    private MilitaryTrainingMapper militaryTrainingMapper;

    /**
     * 查询态势军事训练月统计情况
     * 
     * @param id 态势军事训练月统计情况主键
     * @return 态势军事训练月统计情况
     */
    @Override
    public MilitaryTraining selectMilitaryTrainingById(Long id)
    {
        return militaryTrainingMapper.selectMilitaryTrainingById(id);
    }

    /**
     * 查询态势军事训练月统计情况列表
     * 
     * @param militaryTraining 态势军事训练月统计情况
     * @return 态势军事训练月统计情况
     */
    @Override
    public MilitaryTraining selectMilitaryTrainingList(MilitaryTraining militaryTraining)
    {
        return militaryTrainingMapper.selectMilitaryTrainingList(militaryTraining);
    }

    /**
     * 新增态势军事训练月统计情况
     * 
     * @param militaryTraining 态势军事训练月统计情况
     * @return 结果
     */
    @Override
    public int insertMilitaryTraining(MilitaryTraining militaryTraining)
    {
        return militaryTrainingMapper.insertMilitaryTraining(militaryTraining);
    }

    /**
     * 修改态势军事训练月统计情况
     * 
     * @param militaryTraining 态势军事训练月统计情况
     * @return 结果
     */
    @Override
    public int updateMilitaryTraining(MilitaryTraining militaryTraining)
    {
        return militaryTrainingMapper.updateMilitaryTraining(militaryTraining);
    }

    /**
     * 批量删除态势军事训练月统计情况
     * 
     * @param ids 需要删除的态势军事训练月统计情况主键
     * @return 结果
     */
    @Override
    public int deleteMilitaryTrainingByIds(Long[] ids)
    {
        return militaryTrainingMapper.deleteMilitaryTrainingByIds(ids);
    }

    /**
     * 删除态势军事训练月统计情况信息
     * 
     * @param id 态势军事训练月统计情况主键
     * @return 结果
     */
    @Override
    public int deleteMilitaryTrainingById(Long id)
    {
        return militaryTrainingMapper.deleteMilitaryTrainingById(id);
    }
}