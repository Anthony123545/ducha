package com.supervision.combatduty.mapper;

import java.util.List;
import com.supervision.combatduty.domain.BdglGrassduty;
import com.supervision.combatduty.domain.dto.ApiStatDutyDto;
import org.apache.ibatis.annotations.Param;

/**
 * 基层值班Mapper接口
 * 
 * @author supervision
 * @date 2022-03-10
 */
public interface BdglGrassdutyMapper 
{
    /**
     * 查询基层值班
     * 
     * @param id 基层值班主键
     * @return 基层值班
     */
    public BdglGrassduty selectBdglGrassdutyById(Long id);

    /**
     * 查询基层值班列表
     * 
     * @param bdglGrassduty 基层值班
     * @return 基层值班集合
     */
    public List<BdglGrassduty> selectBdglGrassdutyList(BdglGrassduty bdglGrassduty);

    /**
     * 新增基层值班
     * 
     * @param bdglGrassduty 基层值班
     * @return 结果
     */
    public int insertBdglGrassduty(BdglGrassduty bdglGrassduty);

    /**
     * 修改基层值班
     * 
     * @param bdglGrassduty 基层值班
     * @return 结果
     */
    public int updateBdglGrassduty(BdglGrassduty bdglGrassduty);

    /**
     * 删除基层值班
     * 
     * @param id 基层值班主键
     * @return 结果
     */
    public int deleteBdglGrassdutyById(Long id);

    /**
     * 批量删除基层值班
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglGrassdutyByIds(Long[] ids);

    List<BdglGrassduty> selectBdglGrassdutyLists(BdglGrassduty bdglGrassduty1);
    
    List<BdglGrassduty> selectGrassdutyList(BdglGrassduty bdglGrassduty);
    
    List<ApiStatDutyDto> selectDutyWithToday(@Param("date") String date, @Param("list") List<Long> deptIdList);
}
