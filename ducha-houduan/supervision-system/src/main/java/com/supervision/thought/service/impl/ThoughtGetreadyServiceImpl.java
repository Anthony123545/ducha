package com.supervision.thought.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtGetreadyMapper;
import com.supervision.thought.domain.ThoughtGetready;
import com.supervision.thought.service.IThoughtGetreadyService;

/**
 * 教育准备会Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-06
 */
@Service
public class ThoughtGetreadyServiceImpl implements IThoughtGetreadyService 
{
    @Autowired
    private ThoughtGetreadyMapper thoughtGetreadyMapper;

    /**
     * 查询教育准备会
     * 
     * @param id 教育准备会主键
     * @return 教育准备会
     */
    @Override
    public ThoughtGetready selectThoughtGetreadyById(Long id)
    {
        return thoughtGetreadyMapper.selectThoughtGetreadyById(id);
    }

    /**
     * 查询教育准备会列表
     * 
     * @param thoughtGetready 教育准备会
     * @return 教育准备会
     */
    @Override
    public List<ThoughtGetready> selectThoughtGetreadyList(ThoughtGetready thoughtGetready)
    {
        return thoughtGetreadyMapper.selectThoughtGetreadyList(thoughtGetready);
    }

    /**
     * 新增教育准备会
     * 
     * @param thoughtGetready 教育准备会
     * @return 结果
     */
    @Override
    public int insertThoughtGetready(ThoughtGetready thoughtGetready)
    {
        thoughtGetready.setCreateTime(DateUtils.getNowDate());
        return thoughtGetreadyMapper.insertThoughtGetready(thoughtGetready);
    }

    /**
     * 修改教育准备会
     * 
     * @param thoughtGetready 教育准备会
     * @return 结果
     */
    @Override
    public int updateThoughtGetready(ThoughtGetready thoughtGetready)
    {
        thoughtGetready.setUpdateTime(DateUtils.getNowDate());
        return thoughtGetreadyMapper.updateThoughtGetready(thoughtGetready);
    }

    /**
     * 批量删除教育准备会
     * 
     * @param ids 需要删除的教育准备会主键
     * @return 结果
     */
    @Override
    public int deleteThoughtGetreadyByIds(Long[] ids)
    {
        return thoughtGetreadyMapper.deleteThoughtGetreadyByIds(ids);
    }

    /**
     * 删除教育准备会信息
     * 
     * @param id 教育准备会主键
     * @return 结果
     */
    @Override
    public int deleteThoughtGetreadyById(Long id)
    {
        return thoughtGetreadyMapper.deleteThoughtGetreadyById(id);
    }
}
