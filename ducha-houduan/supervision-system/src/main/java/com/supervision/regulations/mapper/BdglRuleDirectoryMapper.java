package com.supervision.regulations.mapper;

import java.util.List;
import com.supervision.regulations.domain.BdglRuleDirectory;

/**
 * 规章制度目录Mapper接口
 * 
 * @author supervision
 * @date 2022-03-15
 */
public interface BdglRuleDirectoryMapper 
{
    /**
     * 查询规章制度目录
     * 
     * @param id 规章制度目录主键
     * @return 规章制度目录
     */
    public BdglRuleDirectory selectBdglRuleDirectoryById(Long id);

    /**
     * 查询规章制度目录列表
     * 
     * @param bdglRuleDirectory 规章制度目录
     * @return 规章制度目录集合
     */
    public List<BdglRuleDirectory> selectBdglRuleDirectoryList(BdglRuleDirectory bdglRuleDirectory);

    /**
     * 新增规章制度目录
     * 
     * @param bdglRuleDirectory 规章制度目录
     * @return 结果
     */
    public int insertBdglRuleDirectory(BdglRuleDirectory bdglRuleDirectory);

    /**
     * 修改规章制度目录
     * 
     * @param bdglRuleDirectory 规章制度目录
     * @return 结果
     */
    public int updateBdglRuleDirectory(BdglRuleDirectory bdglRuleDirectory);

    /**
     * 删除规章制度目录
     * 
     * @param id 规章制度目录主键
     * @return 结果
     */
    public int deleteBdglRuleDirectoryById(Long id);

    /**
     * 批量删除规章制度目录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglRuleDirectoryByIds(Long[] ids);

    List<BdglRuleDirectory> selectBdglRuleDirectoryLists(BdglRuleDirectory bdglRuleDirectory);
}
