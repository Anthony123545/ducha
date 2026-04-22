package com.supervision.peopleManage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleManage.mapper.BdglPeopleTransferMapper;
import com.supervision.peopleManage.domain.BdglPeopleTransfer;
import com.supervision.peopleManage.service.IBdglPeopleTransferService;

/**
 * 调动管理Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-23
 */
@Service
public class BdglPeopleTransferServiceImpl implements IBdglPeopleTransferService 
{
    @Autowired
    private BdglPeopleTransferMapper bdglPeopleTransferMapper;

    /**
     * 查询调动管理
     * 
     * @param id 调动管理主键
     * @return 调动管理
     */
    @Override
    public BdglPeopleTransfer selectBdglPeopleTransferById(Integer id)
    {
        return bdglPeopleTransferMapper.selectBdglPeopleTransferById(id);
    }

    /**
     * 查询调动管理列表
     * 
     * @param bdglPeopleTransfer 调动管理
     * @return 调动管理
     */
    @Override
    public List<BdglPeopleTransfer> selectBdglPeopleTransferList(BdglPeopleTransfer bdglPeopleTransfer)
    {
        return bdglPeopleTransferMapper.selectBdglPeopleTransferList(bdglPeopleTransfer);
    }

    /**
     * 新增调动管理
     * 
     * @param bdglPeopleTransfer 调动管理
     * @return 结果
     */
    @Override
    public int insertBdglPeopleTransfer(BdglPeopleTransfer bdglPeopleTransfer)
    {
        return bdglPeopleTransferMapper.insertBdglPeopleTransfer(bdglPeopleTransfer);
    }

    /**
     * 修改调动管理
     * 
     * @param bdglPeopleTransfer 调动管理
     * @return 结果
     */
    @Override
    public int updateBdglPeopleTransfer(BdglPeopleTransfer bdglPeopleTransfer)
    {
        return bdglPeopleTransferMapper.updateBdglPeopleTransfer(bdglPeopleTransfer);
    }

    /**
     * 批量删除调动管理
     * 
     * @param ids 需要删除的调动管理主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleTransferByIds(Integer[] ids)
    {
        return bdglPeopleTransferMapper.deleteBdglPeopleTransferByIds(ids);
    }

    /**
     * 删除调动管理信息
     * 
     * @param id 调动管理主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleTransferById(Integer id)
    {
        return bdglPeopleTransferMapper.deleteBdglPeopleTransferById(id);
    }
}
