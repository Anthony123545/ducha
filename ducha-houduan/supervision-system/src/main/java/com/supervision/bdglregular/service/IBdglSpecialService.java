package com.supervision.bdglregular.service;

import java.util.List;

import com.supervision.bdglregular.domain.BdglRegular;
import com.supervision.bdglregular.domain.BdglSpecial;

/**
 * 专项检查Service接口
 * 
 * @author supervision
 * @date 2022-03-07
 */
public interface IBdglSpecialService 
{
    /**
     * 查询专项检查
     * 
     * @param id 专项检查主键
     * @return 专项检查
     */
    public BdglSpecial selectBdglSpecialById(Long id);

    /**
     * 查询专项检查列表
     * 
     * @param bdglSpecial 专项检查
     * @return 专项检查集合
     */
    public List<BdglSpecial> selectBdglSpecialList(BdglSpecial bdglSpecial);

    /**
     * 新增专项检查
     * 
     * @param bdglSpecial 专项检查
     * @return 结果
     */
    public int insertBdglSpecial(BdglSpecial bdglSpecial);

    /**
     * 修改专项检查
     * 
     * @param bdglSpecial 专项检查
     * @return 结果
     */
    public int updateBdglSpecial(BdglSpecial bdglSpecial);

    /**
     * 批量删除专项检查
     * 
     * @param ids 需要删除的专项检查主键集合
     * @return 结果
     */
    public int deleteBdglSpecialByIds(Long[] ids);

    /**
     * 根据部门ID 查出所有数据
     * @param deptId
     * @return
     */
    public List<BdglSpecial> selectDeptById(long deptId);

    /**
     * 查询当前部门以及部门下减分状态的数据
     * @param deptId
     * @return
     */
    public List<BdglSpecial> selectDeptByIdAndStates(long deptId);

    /**
     * 删除专项检查信息
     * 
     * @param id 专项检查主键
     * @return 结果
     */
    public int deleteBdglSpecialById(Long id);

    List<BdglSpecial> selectBdglSpecialLists(BdglSpecial bdglRegulars);
}
