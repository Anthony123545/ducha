package com.supervision.combatduty.service;

import java.util.List;
import com.supervision.combatduty.domain.OutPeopleContrast;

/**
 * 外出人员对比设置Service接口
 *
 * @author ruoyi
 * @date 2023-04-07
 */
public interface IOutPeopleContrastService
{
    /**
     * 查询外出人员对比设置
     *
     * @param id 外出人员对比设置主键
     * @return 外出人员对比设置
     */
    public OutPeopleContrast selectOutPeopleContrastById(Integer id);

    /**
     * 查询外出人员对比设置列表
     *
     * @param outPeopleContrast 外出人员对比设置
     * @return 外出人员对比设置集合
     */
    public List<OutPeopleContrast> selectOutPeopleContrastList(OutPeopleContrast outPeopleContrast);

    /**
     * 新增外出人员对比设置
     *
     * @param outPeopleContrast 外出人员对比设置
     * @return 结果
     */
    public int insertOutPeopleContrast(OutPeopleContrast outPeopleContrast);

    /**
     * 修改外出人员对比设置
     *
     * @param outPeopleContrast 外出人员对比设置
     * @return 结果
     */
    public int updateOutPeopleContrast(OutPeopleContrast outPeopleContrast);

    /**
     * 批量删除外出人员对比设置
     *
     * @param ids 需要删除的外出人员对比设置主键集合
     * @return 结果
     */
    public int deleteOutPeopleContrastByIds(Integer[] ids);

    /**
     * 删除外出人员对比设置信息
     *
     * @param id 外出人员对比设置主键
     * @return 结果
     */
    public int deleteOutPeopleContrastById(Integer id);
}