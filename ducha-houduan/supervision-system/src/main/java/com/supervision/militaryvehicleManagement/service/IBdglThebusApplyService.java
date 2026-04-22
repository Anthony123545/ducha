package com.supervision.militaryvehicleManagement.service;

import java.util.List;
import com.supervision.militaryvehicleManagement.domain.BdglThebusApply;

/**
 * 用车申请Service接口
 *
 * @author supervision
 * @date 2022-03-04
 */
public interface IBdglThebusApplyService
{
    /**
     * 查询用车申请
     *
     * @param id 用车申请主键
     * @return 用车申请
     */
    public BdglThebusApply selectBdglThebusApplyById(Long id);

    /**
     * 查询用车申请列表
     *
     * @param bdglThebusApply 用车申请
     * @return 用车申请集合
     */
    public List<BdglThebusApply> selectBdglThebusApplyList(BdglThebusApply bdglThebusApply);

    /**
     * 新增用车申请
     *
     * @param bdglThebusApply 用车申请
     * @return 结果
     */
    public int insertBdglThebusApply(BdglThebusApply bdglThebusApply);

    /**
     * 修改用车申请
     *
     * @param bdglThebusApply 用车申请
     * @return 结果
     */
    public int updateBdglThebusApply(BdglThebusApply bdglThebusApply);

    /**
     * 批量删除用车申请
     *
     * @param ids 需要删除的用车申请主键集合
     * @return 结果
     */
    public int deleteBdglThebusApplyByIds(Long[] ids);

    public int updateKeChuChe(BdglThebusApply bdglThebusApply);

    /**
     * 删除用车申请信息
     *
     * @param id 用车申请主键
     * @return 结果
     */
    public int deleteBdglThebusApplyById(Long id);

    public int deleteThebusApplyById(Long id);

    List<BdglThebusApply> selectBdglThebusApplyLists(BdglThebusApply bdglThebusApply);

    BdglThebusApply selectBdglThebusApplyByCarIds(String license);
}
