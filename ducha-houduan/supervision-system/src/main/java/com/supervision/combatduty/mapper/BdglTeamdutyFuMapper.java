package com.supervision.combatduty.mapper;

import java.util.List;
import com.supervision.combatduty.domain.BdglTeamdutyFu;

/**
 * 大队值班分表Mapper接口
 * 
 * @author supervision
 * @date 2022-05-06
 */
public interface BdglTeamdutyFuMapper 
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
     * 删除大队值班分表
     * 
     * @param id 大队值班分表主键
     * @return 结果
     */
    public int deleteBdglTeamdutyFuById(Long id);

    /**
     * 批量删除大队值班分表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglTeamdutyFuByIds(Long[] ids);
}
