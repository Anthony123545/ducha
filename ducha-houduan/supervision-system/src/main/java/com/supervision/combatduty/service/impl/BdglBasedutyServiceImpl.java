package com.supervision.combatduty.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.combatduty.mapper.BdglBasedutyMapper;
import com.supervision.combatduty.domain.BdglBaseduty;
import com.supervision.combatduty.service.IBdglBasedutyService;

/**
 * 基地值班Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-10
 */
@Service
public class BdglBasedutyServiceImpl implements IBdglBasedutyService 
{
    @Autowired
    private BdglBasedutyMapper bdglBasedutyMapper;

    /**
     * 查询基地值班
     * 
     * @param id 基地值班主键
     * @return 基地值班
     */
    @Override
    public BdglBaseduty selectBdglBasedutyById(Long id)
    {
        return bdglBasedutyMapper.selectBdglBasedutyById(id);
    }

    /**
     * 查询基地值班列表
     * 
     * @param bdglBaseduty 基地值班
     * @return 基地值班
     */
    @Override
    public List<BdglBaseduty> selectBdglBasedutyList(BdglBaseduty bdglBaseduty)
    {
        return bdglBasedutyMapper.selectBdglBasedutyList(bdglBaseduty);
    }

    /**
     * 新增基地值班
     * 
     * @param bdglBaseduty 基地值班
     * @return 结果
     */
    @Override
    public int insertBdglBaseduty(BdglBaseduty bdglBaseduty)
    {
        return bdglBasedutyMapper.insertBdglBaseduty(bdglBaseduty);
    }

    /**
     * 修改基地值班
     * 
     * @param bdglBaseduty 基地值班
     * @return 结果
     */
    @Override
    public int updateBdglBaseduty(BdglBaseduty bdglBaseduty)
    {
        return bdglBasedutyMapper.updateBdglBaseduty(bdglBaseduty);
    }

    /**
     * 批量删除基地值班
     * 
     * @param ids 需要删除的基地值班主键
     * @return 结果
     */
    @Override
    public int deleteBdglBasedutyByIds(Long[] ids)
    {
        return bdglBasedutyMapper.deleteBdglBasedutyByIds(ids);
    }

    /**
     * 删除基地值班信息
     * 
     * @param id 基地值班主键
     * @return 结果
     */
    @Override
    public int deleteBdglBasedutyById(Long id)
    {
        return bdglBasedutyMapper.deleteBdglBasedutyById(id);
    }
}
