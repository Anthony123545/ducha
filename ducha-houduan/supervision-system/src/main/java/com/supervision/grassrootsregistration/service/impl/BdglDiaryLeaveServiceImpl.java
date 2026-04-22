package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglDiaryLeaveMapper;
import com.supervision.grassrootsregistration.domain.BdglDiaryLeave;
import com.supervision.grassrootsregistration.service.IBdglDiaryLeaveService;

/**
 * 要事日记请假Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-19
 */
@Service
public class BdglDiaryLeaveServiceImpl implements IBdglDiaryLeaveService 
{
    @Autowired
    private BdglDiaryLeaveMapper bdglDiaryLeaveMapper;

    /**
     * 查询要事日记请假
     * 
     * @param id 要事日记请假主键
     * @return 要事日记请假
     */
    @Override
    public BdglDiaryLeave selectBdglDiaryLeaveById(Long id)
    {
        return bdglDiaryLeaveMapper.selectBdglDiaryLeaveById(id);
    }

    /**
     * 查询要事日记请假列表
     * 
     * @param bdglDiaryLeave 要事日记请假
     * @return 要事日记请假
     */
    @Override
    public List<BdglDiaryLeave> selectBdglDiaryLeaveList(BdglDiaryLeave bdglDiaryLeave)
    {
        return bdglDiaryLeaveMapper.selectBdglDiaryLeaveList(bdglDiaryLeave);
    }

    /**
     * 新增要事日记请假
     * 
     * @param bdglDiaryLeave 要事日记请假
     * @return 结果
     */
    @Override
    public int insertBdglDiaryLeave(BdglDiaryLeave bdglDiaryLeave)
    {
        bdglDiaryLeave.setCreateTime(DateUtils.getNowDate());
        return bdglDiaryLeaveMapper.insertBdglDiaryLeave(bdglDiaryLeave);
    }

    /**
     * 修改要事日记请假
     * 
     * @param bdglDiaryLeave 要事日记请假
     * @return 结果
     */
    @Override
    public int updateBdglDiaryLeave(BdglDiaryLeave bdglDiaryLeave)
    {
        return bdglDiaryLeaveMapper.updateBdglDiaryLeave(bdglDiaryLeave);
    }

    /**
     * 批量删除要事日记请假
     * 
     * @param ids 需要删除的要事日记请假主键
     * @return 结果
     */
    @Override
    public int deleteBdglDiaryLeaveByIds(Long[] ids)
    {
        return bdglDiaryLeaveMapper.deleteBdglDiaryLeaveByIds(ids);
    }

    /**
     * 删除要事日记请假信息
     * 
     * @param id 要事日记请假主键
     * @return 结果
     */
    @Override
    public int deleteBdglDiaryLeaveById(Long id)
    {
        return bdglDiaryLeaveMapper.deleteBdglDiaryLeaveById(id);
    }
}
