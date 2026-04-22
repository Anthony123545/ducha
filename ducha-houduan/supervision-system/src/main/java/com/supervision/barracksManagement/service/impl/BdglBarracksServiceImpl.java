package com.supervision.barracksManagement.service.impl;

import java.util.List;

import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.barracksManagement.mapper.BdglBarracksMapper;
import com.supervision.barracksManagement.domain.BdglBarracks;
import com.supervision.barracksManagement.service.IBdglBarracksService;

/**
 * 营房档案Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-02
 */
@Service
public class BdglBarracksServiceImpl implements IBdglBarracksService 
{
    @Autowired
    private BdglBarracksMapper bdglBarracksMapper;

    /**
     * 查询营房档案
     * 
     * @param id 营房档案主键
     * @return 营房档案
     */
    @Override
    public BdglBarracks selectBdglBarracksById(Integer id)
    {
        return bdglBarracksMapper.selectBdglBarracksById(id);
    }

    /**
     * 查询营房档案列表
     * 
     * @param bdglBarracks 营房档案
     * @return 营房档案
     */
    @Override
    public List<BdglBarracks> selectBdglBarracksList(BdglBarracks bdglBarracks)
    {
        return bdglBarracksMapper.selectBdglBarracksList(bdglBarracks);
    }

    /**
     * 新增营房档案
     * 
     * @param bdglBarracks 营房档案
     * @return 结果
     */
    @Override
    public int insertBdglBarracks(BdglBarracks bdglBarracks)
    {
        bdglBarracks.setCreatetime(DateUtils.getNowDate());
        return bdglBarracksMapper.insertBdglBarracks(bdglBarracks);
    }

    /**
     * 修改营房档案
     * 
     * @param bdglBarracks 营房档案
     * @return 结果
     */
    @Override
    public int updateBdglBarracks(BdglBarracks bdglBarracks)
    {
        bdglBarracks.setUpdatetime(DateUtils.getNowDate());
        return bdglBarracksMapper.updateBdglBarracks(bdglBarracks);
    }

    /**
     * 批量删除营房档案
     * 
     * @param ids 需要删除的营房档案主键
     * @return 结果
     */
    @Override
    public int deleteBdglBarracksByIds(Integer[] ids)
    {
        return bdglBarracksMapper.deleteBdglBarracksByIds(ids);
    }

    /**
     * 删除营房档案信息
     * 
     * @param id 营房档案主键
     * @return 结果
     */
    @Override
    public int deleteBdglBarracksById(Integer id)
    {
        return bdglBarracksMapper.deleteBdglBarracksById(id);
    }
    
    @Override
    public Integer selectBdglBarracksCount() {
        return bdglBarracksMapper.selectBdglBarracksCount();
    }
}
