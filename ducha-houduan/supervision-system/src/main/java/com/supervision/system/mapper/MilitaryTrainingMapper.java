package com.supervision.system.mapper;

import java.util.List;
import com.supervision.system.domain.MilitaryTraining;

/**
 * 态势军事训练月统计情况Mapper接口
 * 
 * @author ruoyi
 * @date 2022-05-21
 */
public interface MilitaryTrainingMapper 
{
    /**
     * 查询态势军事训练月统计情况
     * 
     * @param id 态势军事训练月统计情况主键
     * @return 态势军事训练月统计情况
     */
    public MilitaryTraining selectMilitaryTrainingById(Long id);

    /**
     * 查询态势军事训练月统计情况列表
     * 
     * @param militaryTraining 态势军事训练月统计情况
     * @return 态势军事训练月统计情况集合
     */
    public MilitaryTraining selectMilitaryTrainingList(MilitaryTraining militaryTraining);

    /**
     * 新增态势军事训练月统计情况
     * 
     * @param militaryTraining 态势军事训练月统计情况
     * @return 结果
     */
    public int insertMilitaryTraining(MilitaryTraining militaryTraining);

    /**
     * 修改态势军事训练月统计情况
     * 
     * @param militaryTraining 态势军事训练月统计情况
     * @return 结果
     */
    public int updateMilitaryTraining(MilitaryTraining militaryTraining);

    /**
     * 删除态势军事训练月统计情况
     * 
     * @param id 态势军事训练月统计情况主键
     * @return 结果
     */
    public int deleteMilitaryTrainingById(Long id);

    /**
     * 批量删除态势军事训练月统计情况
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMilitaryTrainingByIds(Long[] ids);
}