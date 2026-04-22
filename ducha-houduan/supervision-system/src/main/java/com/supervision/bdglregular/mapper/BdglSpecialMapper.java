package com.supervision.bdglregular.mapper;

import java.util.List;

import com.supervision.bdglregular.domain.BdglRegular;
import com.supervision.bdglregular.domain.BdglSpecial;
import org.apache.ibatis.annotations.Param;

/**
 * 专项检查Mapper接口
 * 
 * @author supervision
 * @date 2022-03-07
 */
public interface BdglSpecialMapper 
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
     * 删除专项检查
     * 
     * @param id 专项检查主键
     * @return 结果
     */
    public int deleteBdglSpecialById(Long id);

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
     * 批量删除专项检查
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglSpecialByIds(Long[] ids);

    List<BdglSpecial> selectBdglSpecialLists(BdglSpecial bdglRegulars);
}
