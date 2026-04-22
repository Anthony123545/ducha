package com.supervision.medicalhealth.service;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglExamineDetails;

/**
 * 体检信息Service接口
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public interface IBdglExamineDetailsService 
{
    /**
     * 查询体检信息
     * 
     * @param id 体检信息主键
     * @return 体检信息
     */
    public BdglExamineDetails selectBdglExamineDetailsById(Integer id);

    /**
     * 查询体检信息列表
     * 
     * @param bdglExamineDetails 体检信息
     * @return 体检信息集合
     */
    public List<BdglExamineDetails> selectBdglExamineDetailsList(BdglExamineDetails bdglExamineDetails);

    /**
     * 新增体检信息
     * 
     * @param bdglExamineDetails 体检信息
     * @return 结果
     */
    public int insertBdglExamineDetails(BdglExamineDetails bdglExamineDetails);

    /**
     * 修改体检信息
     * 
     * @param bdglExamineDetails 体检信息
     * @return 结果
     */
    public int updateBdglExamineDetails(BdglExamineDetails bdglExamineDetails);

    /**
     * 批量删除体检信息
     * 
     * @param ids 需要删除的体检信息主键集合
     * @return 结果
     */
    public int deleteBdglExamineDetailsByIds(Integer[] ids);

    /**
     * 删除体检信息信息
     * 
     * @param id 体检信息主键
     * @return 结果
     */
    public int deleteBdglExamineDetailsById(Integer id);
}