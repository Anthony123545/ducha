package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration .domain.BdglSoldier;
import org.apache.ibatis.annotations.Param;

/**
 * 军人委员会会议登记本Mapper接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface BdglSoldierMapper 
{
    /**
     * 查询军人委员会会议登记本
     * 
     * @param id 军人委员会会议登记本主键
     * @return 军人委员会会议登记本
     */
    public BdglSoldier selectBdglSoldierById(Integer id);

    /**
     * 查询军人委员会会议登记本列表
     * 
     * @param bdglSoldier 军人委员会会议登记本
     * @return 军人委员会会议登记本集合
     */
    public List<BdglSoldier> selectBdglSoldierList(BdglSoldier bdglSoldier);

    /**
     * 新增军人委员会会议登记本
     * 
     * @param bdglSoldier 军人委员会会议登记本
     * @return 结果
     */
    public int insertBdglSoldier(BdglSoldier bdglSoldier);

    /**
     * 修改军人委员会会议登记本
     * 
     * @param bdglSoldier 军人委员会会议登记本
     * @return 结果
     */
    public int updateBdglSoldier(BdglSoldier bdglSoldier);

    /**
     * 删除军人委员会会议登记本
     * 
     * @param id 军人委员会会议登记本主键
     * @return 结果
     */
    public int deleteBdglSoldierById(Integer id);

    /**
     * 批量删除军人委员会会议登记本
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglSoldierByIds(Integer[] ids);
    
    int selectSoldierByunitId(@Param("unitId") int unitId, @Param("year") String year);
    
    List<BdglSoldier> selectSoldierOnUnitList(@Param("unitId") int unitId, @Param("year") String year);
}
