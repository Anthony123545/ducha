package com.supervision.combatduty.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.combatduty.mapper.BdglGrassdutyMapper;
import com.supervision.combatduty.domain.BdglGrassduty;
import com.supervision.combatduty.service.IBdglGrassdutyService;

/**
 * 基层值班Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-10
 */
@Service
public class BdglGrassdutyServiceImpl implements IBdglGrassdutyService 
{
    @Autowired
    private BdglGrassdutyMapper bdglGrassdutyMapper;

    /**
     * 查询基层值班
     * 
     * @param id 基层值班主键
     * @return 基层值班
     */
    @Override
    public BdglGrassduty selectBdglGrassdutyById(Long id)
    {
        return bdglGrassdutyMapper.selectBdglGrassdutyById(id);
    }

    /**
     * 查询基层值班列表
     * 
     * @param bdglGrassduty 基层值班
     * @return 基层值班
     */
    @Override
    public List<BdglGrassduty> selectBdglGrassdutyList(BdglGrassduty bdglGrassduty)
    {
        return bdglGrassdutyMapper.selectBdglGrassdutyList(bdglGrassduty);
    }

    /**
     * 新增基层值班
     * 
     * @param bdglGrassduty 基层值班
     * @return 结果
     */
    @Override
    public int insertBdglGrassduty(BdglGrassduty bdglGrassduty)
    {
        return bdglGrassdutyMapper.insertBdglGrassduty(bdglGrassduty);
    }

    /**
     * 修改基层值班
     * 
     * @param bdglGrassduty 基层值班
     * @return 结果
     */
    @Override
    public int updateBdglGrassduty(BdglGrassduty bdglGrassduty)
    {
        return bdglGrassdutyMapper.updateBdglGrassduty(bdglGrassduty);
    }

    /**
     * 批量删除基层值班
     * 
     * @param ids 需要删除的基层值班主键
     * @return 结果
     */
    @Override
    public int deleteBdglGrassdutyByIds(Long[] ids)
    {
        return bdglGrassdutyMapper.deleteBdglGrassdutyByIds(ids);
    }

    /**
     * 删除基层值班信息
     * 
     * @param id 基层值班主键
     * @return 结果
     */
    @Override
    public int deleteBdglGrassdutyById(Long id)
    {
        return bdglGrassdutyMapper.deleteBdglGrassdutyById(id);
    }

    @Override
    public List<BdglGrassduty> selectBdglGrassdutyLists(BdglGrassduty bdglGrassduty1) {
        return bdglGrassdutyMapper.selectBdglGrassdutyLists(bdglGrassduty1);
    }
    
    @Override
    public List<BdglGrassduty> selectGrassdutyList(BdglGrassduty bdglGrassduty) {
        return bdglGrassdutyMapper.selectGrassdutyList(bdglGrassduty);
    }
}
