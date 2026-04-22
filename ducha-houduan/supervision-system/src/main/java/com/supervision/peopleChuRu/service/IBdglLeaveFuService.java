package com.supervision.peopleChuRu.service;

import java.util.List;
import com.supervision.peopleChuRu.domain.BdglLeaveFu;

/**
 * 特殊审批记录Service接口
 * 
 * @author supervision
 * @date 2022-03-18
 */
public interface IBdglLeaveFuService 
{
    /**
     * 查询特殊审批记录
     * 
     * @param id 特殊审批记录主键
     * @return 特殊审批记录
     */
    public BdglLeaveFu selectBdglLeaveFuById(Long id);

    /**
     * 查询特殊审批记录列表
     * 
     * @param bdglLeaveFu 特殊审批记录
     * @return 特殊审批记录集合
     */
    public List<BdglLeaveFu> selectBdglLeaveFuList(BdglLeaveFu bdglLeaveFu);

    /**
     * 新增特殊审批记录
     * 
     * @param bdglLeaveFu 特殊审批记录
     * @return 结果
     */
    public int insertBdglLeaveFu(BdglLeaveFu bdglLeaveFu);

    /**
     * 修改特殊审批记录
     * 
     * @param bdglLeaveFu 特殊审批记录
     * @return 结果
     */
    public int updateBdglLeaveFu(BdglLeaveFu bdglLeaveFu);

    /**
     * 批量删除特殊审批记录
     * 
     * @param ids 需要删除的特殊审批记录主键集合
     * @return 结果
     */
    public int deleteBdglLeaveFuByIds(Long[] ids);

    /**
     * 删除特殊审批记录信息
     * 
     * @param id 特殊审批记录主键
     * @return 结果
     */
    public int deleteBdglLeaveFuById(Long id);
}
