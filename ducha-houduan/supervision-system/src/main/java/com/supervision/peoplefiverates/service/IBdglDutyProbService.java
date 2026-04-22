package com.supervision.peoplefiverates.service;

import java.util.List;
import com.supervision.peoplefiverates.domain.BdglDutyProb;

/**
 * 人员五率Service接口
 * 
 * @author supervision
 * @date 2022-03-19
 */
public interface IBdglDutyProbService 
{
    /**
     * 查询人员五率
     * 
     * @param id 人员五率主键
     * @return 人员五率
     */
    public BdglDutyProb selectBdglDutyProbById(Integer id);

    /**
     * 查询人员五率列表
     * 
     * @param bdglDutyProb 人员五率
     * @return 人员五率集合
     */
    public List<BdglDutyProb> selectBdglDutyProbList(BdglDutyProb bdglDutyProb);

    /**
     * 新增人员五率
     * 
     * @param bdglDutyProb 人员五率
     * @return 结果
     */
    public int insertBdglDutyProb(BdglDutyProb bdglDutyProb);

    /**
     * 修改人员五率
     * 
     * @param bdglDutyProb 人员五率
     * @return 结果
     */
    public int updateBdglDutyProb(BdglDutyProb bdglDutyProb);

    /**
     * 批量删除人员五率
     * 
     * @param ids 需要删除的人员五率主键集合
     * @return 结果
     */
    public int deleteBdglDutyProbByIds(Integer[] ids);

    /**
     * 删除人员五率信息
     * 
     * @param id 人员五率主键
     * @return 结果
     */
    public int deleteBdglDutyProbById(Integer id);
}
