package com.supervision.combatduty.service;

import java.util.List;
import com.supervision.combatduty.domain.BdglTeamdutyFu;

/**
 * 大队值班分表Service接口
 * 
 * @author supervision
 * @date 2022-05-06
 */
public interface IBdglTeamdutyFuService 
{
    /**
     * 查询大队值班分表
     * 
     * @param id 大队值班分表主键
     * @return 大队值班分表
     */
    public BdglTeamdutyFu selectBdglTeamdutyFuById(Long id);

    /**
     * 查询大队值班分表列表
     * 
     * @param bdglTeamdutyFu 大队值班分表
     * @return 大队值班分表集合
     */
    public List<BdglTeamdutyFu> selectBdglTeamdutyFuList(BdglTeamdutyFu bdglTeamdutyFu);

    /**
     * 新增大队值班分表
     * 
     * @param bdglTeamdutyFu 大队值班分表
     * @return 结果
     */
    public int insertBdglTeamdutyFu(BdglTeamdutyFu bdglTeamdutyFu);

    /**
     * 修改大队值班分表
     * 
     * @param bdglTeamdutyFu 大队值班分表
     * @return 结果
     */
    public int updateBdglTeamdutyFu(BdglTeamdutyFu bdglTeamdutyFu);

    /**
     * 批量删除大队值班分表
     * 
     * @param ids 需要删除的大队值班分表主键集合
     * @return 结果
     */
    public int deleteBdglTeamdutyFuByIds(Long[] ids);

    /**
     * 删除大队值班分表信息
     * 
     * @param id 大队值班分表主键
     * @return 结果
     */
    public int deleteBdglTeamdutyFuById(Long id);
}
