package com.supervision.combatduty.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.combatduty.mapper.BdglTaskdutyFuMapper;
import com.supervision.combatduty.domain.BdglTaskdutyFu;
import com.supervision.combatduty.service.IBdglTaskdutyFuService;

/**
 * 执行任务附表Service业务层处理
 * 
 * @author supervision
 * @date 2022-05-06
 */
@Service
public class BdglTaskdutyFuServiceImpl implements IBdglTaskdutyFuService 
{
    @Autowired
    private BdglTaskdutyFuMapper bdglTaskdutyFuMapper;

    /**
     * 查询执行任务附表
     * 
     * @param id 执行任务附表主键
     * @return 执行任务附表
     */
    @Override
    public BdglTaskdutyFu selectBdglTaskdutyFuById(Long id)
    {
        return bdglTaskdutyFuMapper.selectBdglTaskdutyFuById(id);
    }

    /**
     * 查询执行任务附表列表
     * 
     * @param bdglTaskdutyFu 执行任务附表
     * @return 执行任务附表
     */
    @Override
    public List<BdglTaskdutyFu> selectBdglTaskdutyFuList(BdglTaskdutyFu bdglTaskdutyFu)
    {
        return bdglTaskdutyFuMapper.selectBdglTaskdutyFuList(bdglTaskdutyFu);
    }

    /**
     * 新增执行任务附表
     * 
     * @param bdglTaskdutyFu 执行任务附表
     * @return 结果
     */
    @Override
    public int insertBdglTaskdutyFu(BdglTaskdutyFu bdglTaskdutyFu)
    {
        return bdglTaskdutyFuMapper.insertBdglTaskdutyFu(bdglTaskdutyFu);
    }

    /**
     * 修改执行任务附表
     * 
     * @param bdglTaskdutyFu 执行任务附表
     * @return 结果
     */
    @Override
    public int updateBdglTaskdutyFu(BdglTaskdutyFu bdglTaskdutyFu)
    {
        return bdglTaskdutyFuMapper.updateBdglTaskdutyFu(bdglTaskdutyFu);
    }

    /**
     * 批量删除执行任务附表
     * 
     * @param ids 需要删除的执行任务附表主键
     * @return 结果
     */
    @Override
    public int deleteBdglTaskdutyFuByIds(Long[] ids)
    {
        return bdglTaskdutyFuMapper.deleteBdglTaskdutyFuByIds(ids);
    }

    /**
     * 删除执行任务附表信息
     * 
     * @param id 执行任务附表主键
     * @return 结果
     */
    @Override
    public int deleteBdglTaskdutyFuById(Long id)
    {
        return bdglTaskdutyFuMapper.deleteBdglTaskdutyFuById(id);
    }

    @Override
    public int deleteBdglTaskdutyFu(BdglTaskdutyFu bdglTaskdutyFu) {
        return bdglTaskdutyFuMapper.deleteBdglTaskdutyFu(bdglTaskdutyFu);
    }
}
