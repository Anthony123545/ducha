package com.supervision.combatduty.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.combatduty.mapper.BdglTeamdutyFuMapper;
import com.supervision.combatduty.domain.BdglTeamdutyFu;
import com.supervision.combatduty.service.IBdglTeamdutyFuService;

/**
 * 大队值班分表Service业务层处理
 * 
 * @author supervision
 * @date 2022-05-06
 */
@Service
public class BdglTeamdutyFuServiceImpl implements IBdglTeamdutyFuService 
{
    @Autowired
    private BdglTeamdutyFuMapper bdglTeamdutyFuMapper;

    /**
     * 查询大队值班分表
     * 
     * @param id 大队值班分表主键
     * @return 大队值班分表
     */
    @Override
    public BdglTeamdutyFu selectBdglTeamdutyFuById(Long id)
    {
        return bdglTeamdutyFuMapper.selectBdglTeamdutyFuById(id);
    }

    /**
     * 查询大队值班分表列表
     * 
     * @param bdglTeamdutyFu 大队值班分表
     * @return 大队值班分表
     */
    @Override
    public List<BdglTeamdutyFu> selectBdglTeamdutyFuList(BdglTeamdutyFu bdglTeamdutyFu)
    {
        return bdglTeamdutyFuMapper.selectBdglTeamdutyFuList(bdglTeamdutyFu);
    }

    /**
     * 新增大队值班分表
     * 
     * @param bdglTeamdutyFu 大队值班分表
     * @return 结果
     */
    @Override
    public int insertBdglTeamdutyFu(BdglTeamdutyFu bdglTeamdutyFu)
    {
        return bdglTeamdutyFuMapper.insertBdglTeamdutyFu(bdglTeamdutyFu);
    }

    /**
     * 修改大队值班分表
     * 
     * @param bdglTeamdutyFu 大队值班分表
     * @return 结果
     */
    @Override
    public int updateBdglTeamdutyFu(BdglTeamdutyFu bdglTeamdutyFu)
    {
        return bdglTeamdutyFuMapper.updateBdglTeamdutyFu(bdglTeamdutyFu);
    }

    /**
     * 批量删除大队值班分表
     * 
     * @param ids 需要删除的大队值班分表主键
     * @return 结果
     */
    @Override
    public int deleteBdglTeamdutyFuByIds(Long[] ids)
    {
        return bdglTeamdutyFuMapper.deleteBdglTeamdutyFuByIds(ids);
    }

    /**
     * 删除大队值班分表信息
     * 
     * @param id 大队值班分表主键
     * @return 结果
     */
    @Override
    public int deleteBdglTeamdutyFuById(Long id)
    {
        return bdglTeamdutyFuMapper.deleteBdglTeamdutyFuById(id);
    }
}
