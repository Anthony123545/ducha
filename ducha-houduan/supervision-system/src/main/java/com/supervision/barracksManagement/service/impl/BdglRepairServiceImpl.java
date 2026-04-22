package com.supervision.barracksManagement.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.barracksManagement.mapper.BdglRepairMapper;
import com.supervision.barracksManagement.domain.BdglRepair;
import com.supervision.barracksManagement.service.IBdglRepairService;

/**
 * 维修申请Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@Service
public class BdglRepairServiceImpl implements IBdglRepairService 
{
    @Autowired
    private BdglRepairMapper bdglRepairMapper;

    /**
     * 查询维修申请
     * 
     * @param id 维修申请主键
     * @return 维修申请
     */
    @Override
    public BdglRepair selectBdglRepairById(Long id)
    {
        return bdglRepairMapper.selectBdglRepairById(id);
    }

    /**
     * 查询维修申请列表
     * 
     * @param bdglRepair 维修申请
     * @return 维修申请
     */
    @Override
    public List<BdglRepair> selectBdglRepairList(BdglRepair bdglRepair)
    {
        return bdglRepairMapper.selectBdglRepairList(bdglRepair);
    }

    /**
     * 新增维修申请
     * 
     * @param bdglRepair 维修申请
     * @return 结果
     */
    @Override
    public int insertBdglRepair(BdglRepair bdglRepair)
    {
        return bdglRepairMapper.insertBdglRepair(bdglRepair);
    }

    /**
     * 修改维修申请
     * 
     * @param bdglRepair 维修申请
     * @return 结果
     */
    @Override
    public int updateBdglRepair(BdglRepair bdglRepair)
    {
        return bdglRepairMapper.updateBdglRepair(bdglRepair);
    }

    /**
     * 批量删除维修申请
     * 
     * @param ids 需要删除的维修申请主键
     * @return 结果
     */
    @Override
    public int deleteBdglRepairByIds(Long[] ids)
    {
        return bdglRepairMapper.deleteBdglRepairByIds(ids);
    }

    /**
     * 删除维修申请信息
     * 
     * @param id 维修申请主键
     * @return 结果
     */
    @Override
    public int deleteBdglRepairById(Long id)
    {
        return bdglRepairMapper.deleteBdglRepairById(id);
    }
}
