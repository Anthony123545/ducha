package com.supervision.thought.service;

import java.util.List;
import com.supervision.thought.domain.ThoughtParliament;

/**
 * 党委议教会Service接口
 * 
 * @author supervision
 * @date 2022-04-06
 */
public interface IThoughtParliamentService 
{
    /**
     * 查询党委议教会
     * 
     * @param id 党委议教会主键
     * @return 党委议教会
     */
    public ThoughtParliament selectThoughtParliamentById(Long id);

    /**
     * 查询党委议教会列表
     * 
     * @param thoughtParliament 党委议教会
     * @return 党委议教会集合
     */
    public List<ThoughtParliament> selectThoughtParliamentList(ThoughtParliament thoughtParliament);

    /**
     * 新增党委议教会
     * 
     * @param thoughtParliament 党委议教会
     * @return 结果
     */
    public int insertThoughtParliament(ThoughtParliament thoughtParliament);

    /**
     * 修改党委议教会
     * 
     * @param thoughtParliament 党委议教会
     * @return 结果
     */
    public int updateThoughtParliament(ThoughtParliament thoughtParliament);

    /**
     * 批量删除党委议教会
     * 
     * @param ids 需要删除的党委议教会主键集合
     * @return 结果
     */
    public int deleteThoughtParliamentByIds(Long[] ids);

    /**
     * 删除党委议教会信息
     * 
     * @param id 党委议教会主键
     * @return 结果
     */
    public int deleteThoughtParliamentById(Long id);
}
