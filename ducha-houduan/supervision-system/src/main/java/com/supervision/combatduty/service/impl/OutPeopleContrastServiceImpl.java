package com.supervision.combatduty.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.combatduty.mapper.OutPeopleContrastMapper;
import com.supervision.combatduty.domain.OutPeopleContrast;
import com.supervision.combatduty.service.IOutPeopleContrastService;

/**
 * 外出人员对比设置Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Service
public class OutPeopleContrastServiceImpl implements IOutPeopleContrastService
{
    @Autowired
    private OutPeopleContrastMapper outPeopleContrastMapper;

    /**
     * 查询外出人员对比设置
     *
     * @param id 外出人员对比设置主键
     * @return 外出人员对比设置
     */
    @Override
    public OutPeopleContrast selectOutPeopleContrastById(Integer id)
    {
        return outPeopleContrastMapper.selectOutPeopleContrastById(id);
    }

    /**
     * 查询外出人员对比设置列表
     *
     * @param outPeopleContrast 外出人员对比设置
     * @return 外出人员对比设置
     */
    @Override
    public List<OutPeopleContrast> selectOutPeopleContrastList(OutPeopleContrast outPeopleContrast)
    {
        return outPeopleContrastMapper.selectOutPeopleContrastList(outPeopleContrast);
    }

    /**
     * 新增外出人员对比设置
     *
     * @param outPeopleContrast 外出人员对比设置
     * @return 结果
     */
    @Override
    public int insertOutPeopleContrast(OutPeopleContrast outPeopleContrast)
    {
        return outPeopleContrastMapper.insertOutPeopleContrast(outPeopleContrast);
    }

    /**
     * 修改外出人员对比设置
     *
     * @param outPeopleContrast 外出人员对比设置
     * @return 结果
     */
    @Override
    public int updateOutPeopleContrast(OutPeopleContrast outPeopleContrast)
    {
        return outPeopleContrastMapper.updateOutPeopleContrast(outPeopleContrast);
    }

    /**
     * 批量删除外出人员对比设置
     *
     * @param ids 需要删除的外出人员对比设置主键
     * @return 结果
     */
    @Override
    public int deleteOutPeopleContrastByIds(Integer[] ids)
    {
        return outPeopleContrastMapper.deleteOutPeopleContrastByIds(ids);
    }

    /**
     * 删除外出人员对比设置信息
     *
     * @param id 外出人员对比设置主键
     * @return 结果
     */
    @Override
    public int deleteOutPeopleContrastById(Integer id)
    {
        return outPeopleContrastMapper.deleteOutPeopleContrastById(id);
    }
}