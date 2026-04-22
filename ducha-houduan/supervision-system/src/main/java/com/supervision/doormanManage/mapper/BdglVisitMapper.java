package com.supervision.doormanManage.mapper;

import java.util.List;
import com.supervision.doormanManage .domain.BdglVisit;

/**
 * 人员来访登记Mapper接口
 * 
 * @author supervision
 * @date 2022-03-19
 */
public interface BdglVisitMapper 
{
    /**
     * 查询人员来访登记
     * 
     * @param id 人员来访登记主键
     * @return 人员来访登记
     */
    public BdglVisit selectBdglVisitById(Integer id);

    /**
     * 查询人员来访登记列表
     * 
     * @param bdglVisit 人员来访登记
     * @return 人员来访登记集合
     */
    public List<BdglVisit> selectBdglVisitList(BdglVisit bdglVisit);

    /**
     * 新增人员来访登记
     * 
     * @param bdglVisit 人员来访登记
     * @return 结果
     */
    public int insertBdglVisit(BdglVisit bdglVisit);

    /**
     * 修改人员来访登记
     * 
     * @param bdglVisit 人员来访登记
     * @return 结果
     */
    public int updateBdglVisit(BdglVisit bdglVisit);

    /**
     * 删除人员来访登记
     * 
     * @param id 人员来访登记主键
     * @return 结果
     */
    public int deleteBdglVisitById(Integer id);

    /**
     * 批量删除人员来访登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglVisitByIds(Integer[] ids);
}
