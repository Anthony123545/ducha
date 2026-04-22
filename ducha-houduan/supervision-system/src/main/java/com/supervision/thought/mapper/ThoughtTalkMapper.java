package com.supervision.thought.mapper;

import java.util.List;
import com.supervision.thought.domain.ThoughtTalk;

/**
 * 谈心记录卡Mapper接口
 * 
 * @author supervision
 * @date 2022-04-06
 */
public interface ThoughtTalkMapper 
{
    /**
     * 查询谈心记录卡
     * 
     * @param id 谈心记录卡主键
     * @return 谈心记录卡
     */
    public ThoughtTalk selectThoughtTalkById(Long id);

    /**
     * 查询谈心记录卡列表
     * 
     * @param thoughtTalk 谈心记录卡
     * @return 谈心记录卡集合
     */
    public List<ThoughtTalk> selectThoughtTalkList(ThoughtTalk thoughtTalk);

    /**
     * 新增谈心记录卡
     * 
     * @param thoughtTalk 谈心记录卡
     * @return 结果
     */
    public int insertThoughtTalk(ThoughtTalk thoughtTalk);

    /**
     * 修改谈心记录卡
     * 
     * @param thoughtTalk 谈心记录卡
     * @return 结果
     */
    public int updateThoughtTalk(ThoughtTalk thoughtTalk);

    /**
     * 删除谈心记录卡
     * 
     * @param id 谈心记录卡主键
     * @return 结果
     */
    public int deleteThoughtTalkById(Long id);

    /**
     * 批量删除谈心记录卡
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteThoughtTalkByIds(Long[] ids);
}
