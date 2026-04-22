package com.supervision.bdglregular.service;

import java.util.List;
import com.supervision.bdglregular.domain.BdglRegular;

/**
 * 经常性检查Service接口
 * 
 * @author supervision
 * @date 2022-03-07
 */
public interface IBdglRegularService 
{
    /**
     * 查询经常性检查
     * 
     * @param id 经常性检查主键
     * @return 经常性检查
     */
    public BdglRegular selectBdglRegularById(Long id);

    /**
     * 查询经常性检查列表
     * 
     * @param bdglRegular 经常性检查
     * @return 经常性检查集合
     */
    public List<BdglRegular> selectBdglRegularList(BdglRegular bdglRegular);

    /**
     * 根据部门ID 进行查询
     * @param bdglRegular
     * @return
     */
    public List<BdglRegular> selectBdglRegularListByDeptId(BdglRegular bdglRegular);

    /**
     * 新增经常性检查
     * 
     * @param bdglRegular 经常性检查
     * @return 结果
     */
    public int insertBdglRegular(BdglRegular bdglRegular);

    /**
     * 修改经常性检查
     * 
     * @param bdglRegular 经常性检查
     * @return 结果
     */
    public int updateBdglRegular(BdglRegular bdglRegular);

    /**
     * 批量删除经常性检查
     * 
     * @param ids 需要删除的经常性检查主键集合
     * @return 结果
     */
    public int deleteBdglRegularByIds(Long[] ids);

    /**
     * 根据部门ID 查出所有数据
     * @param deptId
     * @return
     */
    public List<BdglRegular> selectDeptById(long deptId);

    /**
     * 查询当前部门以及部门下减分状态的数据
     * @param deptId
     * @return
     */
    public List<BdglRegular> selectDeptByIdAndStates(long deptId);

    /**
     * 删除经常性检查信息
     * 
     * @param id 经常性检查主键
     * @return 结果
     */
    public int deleteBdglRegularById(Long id);

    List<BdglRegular> selectBdglRegularLists(BdglRegular bdglRegulars);
}
