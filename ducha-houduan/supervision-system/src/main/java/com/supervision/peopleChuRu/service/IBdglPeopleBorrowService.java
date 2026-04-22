package com.supervision.peopleChuRu.service;

import java.util.List;
import com.supervision.peopleChuRu.domain.BdglPeopleBorrow;

/**
 * 人员借调Service接口
 * 
 * @author supervision
 * @date 2022-03-05
 */
public interface IBdglPeopleBorrowService 
{
    /**
     * 查询人员借调
     * 
     * @param id 人员借调主键
     * @return 人员借调
     */
    public BdglPeopleBorrow selectBdglPeopleBorrowById(Long id);

    /**
     * 查询人员借调列表
     * 
     * @param bdglPeopleBorrow 人员借调
     * @return 人员借调集合
     */
    public List<BdglPeopleBorrow> selectBdglPeopleBorrowList(BdglPeopleBorrow bdglPeopleBorrow);

    /**
     * 新增人员借调
     * 
     * @param bdglPeopleBorrow 人员借调
     * @return 结果
     */
    public int insertBdglPeopleBorrow(BdglPeopleBorrow bdglPeopleBorrow);

    /**
     * 修改人员借调
     * 
     * @param bdglPeopleBorrow 人员借调
     * @return 结果
     */
    public int updateBdglPeopleBorrow(BdglPeopleBorrow bdglPeopleBorrow);

    /**
     * 批量删除人员借调
     * 
     * @param ids 需要删除的人员借调主键集合
     * @return 结果
     */
    public int deleteBdglPeopleBorrowByIds(Long[] ids);

    /**
     * 删除人员借调信息
     * 
     * @param id 人员借调主键
     * @return 结果
     */
    public int deleteBdglPeopleBorrowById(Long id);
}
