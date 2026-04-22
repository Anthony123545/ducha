package com.supervision.peopleManage.service;

import java.util.List;
import com.supervision.peopleManage.domain.BdglPeopleTransfer;

/**
 * 调动管理Service接口
 * 
 * @author supervision
 * @date 2022-02-23
 */
public interface IBdglPeopleTransferService 
{
    /**
     * 查询调动管理
     * 
     * @param id 调动管理主键
     * @return 调动管理
     */
    public BdglPeopleTransfer selectBdglPeopleTransferById(Integer id);

    /**
     * 查询调动管理列表
     * 
     * @param bdglPeopleTransfer 调动管理
     * @return 调动管理集合
     */
    public List<BdglPeopleTransfer> selectBdglPeopleTransferList(BdglPeopleTransfer bdglPeopleTransfer);

    /**
     * 新增调动管理
     * 
     * @param bdglPeopleTransfer 调动管理
     * @return 结果
     */
    public int insertBdglPeopleTransfer(BdglPeopleTransfer bdglPeopleTransfer);

    /**
     * 修改调动管理
     * 
     * @param bdglPeopleTransfer 调动管理
     * @return 结果
     */
    public int updateBdglPeopleTransfer(BdglPeopleTransfer bdglPeopleTransfer);

    /**
     * 批量删除调动管理
     * 
     * @param ids 需要删除的调动管理主键集合
     * @return 结果
     */
    public int deleteBdglPeopleTransferByIds(Integer[] ids);

    /**
     * 删除调动管理信息
     * 
     * @param id 调动管理主键
     * @return 结果
     */
    public int deleteBdglPeopleTransferById(Integer id);
}
