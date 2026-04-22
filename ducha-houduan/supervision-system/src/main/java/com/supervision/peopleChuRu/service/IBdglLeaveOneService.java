package com.supervision.peopleChuRu.service;

import java.util.List;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;

/**
 * 人员请假Service接口
 * 
 * @author supervision
 * @date 2022-03-31
 */
public interface IBdglLeaveOneService 
{
    /**
     * 查询人员请假
     * 
     * @param id 人员请假主键
     * @return 人员请假
     */
    public BdglLeaveOne selectBdglLeaveOneById(Long id);

    /**
     * 查询人员请假列表
     * 
     * @param bdglLeaveOne 人员请假
     * @return 人员请假集合
     */
    public List<BdglLeaveOne> selectBdglLeaveOneList(BdglLeaveOne bdglLeaveOne);

    /**
     * 新增人员请假
     * 
     * @param bdglLeaveOne 人员请假
     * @return 结果
     */
    public int insertBdglLeaveOne(BdglLeaveOne bdglLeaveOne);

    /**
     * 修改人员请假
     * 
     * @param bdglLeaveOne 人员请假
     * @return 结果
     */
    public int updateBdglLeaveOne(BdglLeaveOne bdglLeaveOne);

    /**
     * 批量删除人员请假
     * 
     * @param ids 需要删除的人员请假主键集合
     * @return 结果
     */
    public int deleteBdglLeaveOneByIds(Long[] ids);

    /**
     * 删除人员请假信息
     * 
     * @param id 人员请假主键
     * @return 结果
     */
    public int deleteBdglLeaveOneById(Long id);
    
    List<BdglLeaveOne> selectBdglLeaveOneListes();
    
    List<BdglLeaveOne> selectBdglLeaveOneLists();

    List<BdglLeaveOne> selectBdglLeaveOneByPeopleId(Long id);
}
