package com.supervision.thought.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtParliamentMapper;
import com.supervision.thought.domain.ThoughtParliament;
import com.supervision.thought.service.IThoughtParliamentService;

/**
 * 党委议教会Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-06
 */
@Service
public class ThoughtParliamentServiceImpl implements IThoughtParliamentService 
{
    @Autowired
    private ThoughtParliamentMapper thoughtParliamentMapper;

    /**
     * 查询党委议教会
     * 
     * @param id 党委议教会主键
     * @return 党委议教会
     */
    @Override
    public ThoughtParliament selectThoughtParliamentById(Long id)
    {
        return thoughtParliamentMapper.selectThoughtParliamentById(id);
    }

    /**
     * 查询党委议教会列表
     * 
     * @param thoughtParliament 党委议教会
     * @return 党委议教会
     */
    @Override
    public List<ThoughtParliament> selectThoughtParliamentList(ThoughtParliament thoughtParliament)
    {
        return thoughtParliamentMapper.selectThoughtParliamentList(thoughtParliament);
    }

    /**
     * 新增党委议教会
     * 
     * @param thoughtParliament 党委议教会
     * @return 结果
     */
    @Override
    public int insertThoughtParliament(ThoughtParliament thoughtParliament)
    {
        thoughtParliament.setCreateTime(DateUtils.getNowDate());
        return thoughtParliamentMapper.insertThoughtParliament(thoughtParliament);
    }

    /**
     * 修改党委议教会
     * 
     * @param thoughtParliament 党委议教会
     * @return 结果
     */
    @Override
    public int updateThoughtParliament(ThoughtParliament thoughtParliament)
    {
        thoughtParliament.setUpdateTime(DateUtils.getNowDate());
        return thoughtParliamentMapper.updateThoughtParliament(thoughtParliament);
    }

    /**
     * 批量删除党委议教会
     * 
     * @param ids 需要删除的党委议教会主键
     * @return 结果
     */
    @Override
    public int deleteThoughtParliamentByIds(Long[] ids)
    {
        return thoughtParliamentMapper.deleteThoughtParliamentByIds(ids);
    }

    /**
     * 删除党委议教会信息
     * 
     * @param id 党委议教会主键
     * @return 结果
     */
    @Override
    public int deleteThoughtParliamentById(Long id)
    {
        return thoughtParliamentMapper.deleteThoughtParliamentById(id);
    }
}
