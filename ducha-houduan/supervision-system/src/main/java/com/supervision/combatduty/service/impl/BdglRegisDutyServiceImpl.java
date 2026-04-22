package com.supervision.combatduty.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.combatduty.mapper.BdglRegisDutyMapper;
import com.supervision.combatduty.domain.BdglRegisDuty;
import com.supervision.combatduty.service.IBdglRegisDutyService;

/**
 * 装备登记Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-11
 */
@Service
public class BdglRegisDutyServiceImpl implements IBdglRegisDutyService 
{
    @Autowired
    private BdglRegisDutyMapper bdglRegisDutyMapper;

    /**
     * 查询装备登记
     * 
     * @param id 装备登记主键
     * @return 装备登记
     */
    @Override
    public BdglRegisDuty selectBdglRegisDutyById(Long id)
    {
        return bdglRegisDutyMapper.selectBdglRegisDutyById(id);
    }

    /**
     * 查询装备登记列表
     * 
     * @param bdglRegisDuty 装备登记
     * @return 装备登记
     */
    @Override
    public List<BdglRegisDuty> selectBdglRegisDutyList(BdglRegisDuty bdglRegisDuty)
    {
        return bdglRegisDutyMapper.selectBdglRegisDutyList(bdglRegisDuty);
    }

    /**
     * 新增装备登记
     * 
     * @param bdglRegisDuty 装备登记
     * @return 结果
     */
    @Override
    public int insertBdglRegisDuty(BdglRegisDuty bdglRegisDuty)
    {
        return bdglRegisDutyMapper.insertBdglRegisDuty(bdglRegisDuty);
    }

    /**
     * 修改装备登记
     * 
     * @param bdglRegisDuty 装备登记
     * @return 结果
     */
    @Override
    public int updateBdglRegisDuty(BdglRegisDuty bdglRegisDuty)
    {
        return bdglRegisDutyMapper.updateBdglRegisDuty(bdglRegisDuty);
    }

    /**
     * 批量删除装备登记
     * 
     * @param ids 需要删除的装备登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglRegisDutyByIds(Long[] ids)
    {
        return bdglRegisDutyMapper.deleteBdglRegisDutyByIds(ids);
    }

    /**
     * 删除装备登记信息
     * 
     * @param id 装备登记主键
     * @return 结果
     */
    @Override
    public int deleteBdglRegisDutyById(Long id)
    {
        return bdglRegisDutyMapper.deleteBdglRegisDutyById(id);
    }

    @Override
    public List<BdglRegisDuty> selectBdglRegisDutyNumber(){
        return bdglRegisDutyMapper.selectBdglRegisDutyNumber();
    };
}
