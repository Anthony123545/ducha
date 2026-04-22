package com.supervision.combatduty.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.combatduty.mapper.BdglTaskdutyMapper;
import com.supervision.combatduty.domain.BdglTaskduty;
import com.supervision.combatduty.service.IBdglTaskdutyService;

/**
 * 执行任务Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-11
 */
@Service
public class BdglTaskdutyServiceImpl implements IBdglTaskdutyService 
{
    @Autowired
    private BdglTaskdutyMapper bdglTaskdutyMapper;

    /**
     * 查询执行任务
     * 
     * @param id 执行任务主键
     * @return 执行任务
     */
    @Override
    public BdglTaskduty selectBdglTaskdutyById(Long id)
    {
        return bdglTaskdutyMapper.selectBdglTaskdutyById(id);
    }

    /**
     * 查询执行任务列表
     * 
     * @param bdglTaskduty 执行任务
     * @return 执行任务
     */
    @Override
    public List<BdglTaskduty> selectBdglTaskdutyList(BdglTaskduty bdglTaskduty)
    {
        return bdglTaskdutyMapper.selectBdglTaskdutyList(bdglTaskduty);
    }

    /**
     * 新增执行任务
     * 
     * @param bdglTaskduty 执行任务
     * @return 结果
     */
    @Override
    public int insertBdglTaskduty(BdglTaskduty bdglTaskduty)
    {
        return bdglTaskdutyMapper.insertBdglTaskduty(bdglTaskduty);
    }

    /**
     * 修改执行任务
     * 
     * @param bdglTaskduty 执行任务
     * @return 结果
     */
    @Override
    public int updateBdglTaskduty(BdglTaskduty bdglTaskduty)
    {
        return bdglTaskdutyMapper.updateBdglTaskduty(bdglTaskduty);
    }

    /**
     * 批量删除执行任务
     * 
     * @param ids 需要删除的执行任务主键
     * @return 结果
     */
    @Override
    public int deleteBdglTaskdutyByIds(Long[] ids)
    {
        return bdglTaskdutyMapper.deleteBdglTaskdutyByIds(ids);
    }

    /**
     * 删除执行任务信息
     * 
     * @param id 执行任务主键
     * @return 结果
     */
    @Override
    public int deleteBdglTaskdutyById(Long id)
    {
        return bdglTaskdutyMapper.deleteBdglTaskdutyById(id);
    }
}
