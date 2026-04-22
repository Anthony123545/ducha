package com.supervision.medicalhealth.mapper;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglExamine;

/**
 * 人员体检信息Mapper接口
 * 
 * @author hr
 * @date 2022-02-14
 */
public interface BdglExamineMapper 
{
    /**
     * 查询人员体检信息
     * 
     * @param id 人员体检信息主键
     * @return 人员体检信息
     */
    public BdglExamine selectBdglExamineById(Integer id);

    /**
     * 查询人员体检信息列表
     * 
     * @param bdglExamine 人员体检信息
     * @return 人员体检信息集合
     */
    public List<BdglExamine> selectBdglExamineList(BdglExamine bdglExamine);

    /**
     * 新增人员体检信息
     * 
     * @param bdglExamine 人员体检信息
     * @return 结果
     */
    public int insertBdglExamine(BdglExamine bdglExamine);

    /**
     * 修改人员体检信息
     * 
     * @param bdglExamine 人员体检信息
     * @return 结果
     */
    public int updateBdglExamine(BdglExamine bdglExamine);

    /**
     * 删除人员体检信息
     * 
     * @param id 人员体检信息主键
     * @return 结果
     */
    public int deleteBdglExamineById(Integer id);

    /**
     * 批量删除人员体检信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglExamineByIds(Integer[] ids);
}
