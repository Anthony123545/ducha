package com.supervision.thought.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtTalkMapper;
import com.supervision.thought.domain.ThoughtTalk;
import com.supervision.thought.service.IThoughtTalkService;

/**
 * 谈心记录卡Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-06
 */
@Service
public class ThoughtTalkServiceImpl implements IThoughtTalkService 
{
    @Autowired
    private ThoughtTalkMapper thoughtTalkMapper;

    /**
     * 查询谈心记录卡
     * 
     * @param id 谈心记录卡主键
     * @return 谈心记录卡
     */
    @Override
    public ThoughtTalk selectThoughtTalkById(Long id)
    {
        return thoughtTalkMapper.selectThoughtTalkById(id);
    }

    /**
     * 查询谈心记录卡列表
     * 
     * @param thoughtTalk 谈心记录卡
     * @return 谈心记录卡
     */
    @Override
    public List<ThoughtTalk> selectThoughtTalkList(ThoughtTalk thoughtTalk)
    {
        return thoughtTalkMapper.selectThoughtTalkList(thoughtTalk);
    }

    /**
     * 新增谈心记录卡
     * 
     * @param thoughtTalk 谈心记录卡
     * @return 结果
     */
    @Override
    public int insertThoughtTalk(ThoughtTalk thoughtTalk)
    {
        thoughtTalk.setCreateTime(DateUtils.getNowDate());
        return thoughtTalkMapper.insertThoughtTalk(thoughtTalk);
    }

    /**
     * 修改谈心记录卡
     * 
     * @param thoughtTalk 谈心记录卡
     * @return 结果
     */
    @Override
    public int updateThoughtTalk(ThoughtTalk thoughtTalk)
    {
        thoughtTalk.setUpdateTime(DateUtils.getNowDate());
        return thoughtTalkMapper.updateThoughtTalk(thoughtTalk);
    }

    /**
     * 批量删除谈心记录卡
     * 
     * @param ids 需要删除的谈心记录卡主键
     * @return 结果
     */
    @Override
    public int deleteThoughtTalkByIds(Long[] ids)
    {
        return thoughtTalkMapper.deleteThoughtTalkByIds(ids);
    }

    /**
     * 删除谈心记录卡信息
     * 
     * @param id 谈心记录卡主键
     * @return 结果
     */
    @Override
    public int deleteThoughtTalkById(Long id)
    {
        return thoughtTalkMapper.deleteThoughtTalkById(id);
    }
}
