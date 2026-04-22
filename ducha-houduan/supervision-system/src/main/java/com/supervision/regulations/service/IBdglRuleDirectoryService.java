package com.supervision.regulations.service;

import java.io.IOException;
import java.util.List;

import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.regulations.domain.BdglRuleDirectory;

/**
 * 规章制度目录Service接口
 * 
 * @author supervision
 * @date 2022-03-15
 */
public interface IBdglRuleDirectoryService 
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
    public int insertBdglRuleDirectory(BdglRuleDirectory bdglRuleDirectory, LoginUser loginUser) throws IOException;

    /**
     * 修改规章制度目录
     * 
     * @param bdglRuleDirectory 规章制度目录
     * @return 结果
     */
    public int updateBdglRuleDirectory(BdglRuleDirectory bdglRuleDirectory);

    /**
     * 批量删除规章制度目录
     * 
     * @param ids 需要删除的规章制度目录主键集合
     * @return 结果
     */
    public int deleteBdglRuleDirectoryByIds(Long[] ids);

    /**
     * 删除规章制度目录信息
     * 
     * @param id 规章制度目录主键
     * @return 结果
     */
    public int deleteBdglRuleDirectoryById(Long id);
}
