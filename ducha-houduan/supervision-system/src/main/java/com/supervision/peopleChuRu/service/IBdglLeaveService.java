package com.supervision.peopleChuRu.service;

import java.util.List;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;

/**
 * 人员请假Service接口
 * 
 * @author supervision
 * @date 2022-03-02
 */
public interface IBdglLeaveService 
{
    /**
     * 查询人员请假
     * 
     * @param id 人员请假主键
     * @return 人员请假
     */
    public BdglLeave selectBdglLeaveById(Long id);

    /**
     * 查询人员请假列表
     * 
     * @param bdglLeave 人员请假
     * @return 人员请假集合
     */
    public List<BdglLeave> selectBdglLeaveList(BdglLeave bdglLeave);

    /**
     * 新增人员请假
     * 
     * @param bdglLeave 人员请假
     * @return 结果
     */
    public int insertBdglLeave(BdglLeave bdglLeave);

    /**
     * 修改人员请假
     * 
     * @param bdglLeave 人员请假
     * @return 结果
     */
    public int updateBdglLeave(BdglLeave bdglLeave);

    /**
     * 批量删除人员请假
     * 
     * @param ids 需要删除的人员请假主键集合
     * @return 结果
     */
    public int deleteBdglLeaveByIds(Long[] ids);

    /**
     * 删除人员请假信息
     * 
     * @param id 人员请假主键
     * @return 结果
     */
    public int deleteBdglLeaveById(Long id);
    
    List<BdglLeave> selectBdglLeaveListes();
    
    List<BdglLeave> selectBdglLeaveLists();
    
    List<BdglLeave> selectBdglLeaveListsss(BdglLeave bdglLeave);

    List<BdglLeave> selectBdglLeaveByPeopleId(Long id);

    BdglLeave selectNewLeaveByCarNumber(String license);
}
