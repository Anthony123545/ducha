package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglEquip;
import org.apache.ibatis.annotations.Param;

/**
 * 训练器材/教材登记薄Mapper接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface BdglEquipMapper 
{
    /**
     * 查询训练器材/教材登记薄
     * 
     * @param id 训练器材/教材登记薄主键
     * @return 训练器材/教材登记薄
     */
    public BdglEquip selectBdglEquipById(Long id);

    public List<BdglEquip> selectBdglByunitid(Integer unitid);
    /**
     * 查询训练器材/教材登记薄列表
     * 
     * @param bdglEquip 训练器材/教材登记薄
     * @return 训练器材/教材登记薄集合
     */
    public List<BdglEquip> selectBdglEquipList(BdglEquip bdglEquip);

    /**
     * 新增训练器材/教材登记薄
     * 
     * @param bdglEquip 训练器材/教材登记薄
     * @return 结果
     */
    public int insertBdglEquip(BdglEquip bdglEquip);

    /**
     * 修改训练器材/教材登记薄
     * 
     * @param bdglEquip 训练器材/教材登记薄
     * @return 结果
     */
    public int updateBdglEquip(BdglEquip bdglEquip);

    /**
     * 删除训练器材/教材登记薄
     * 
     * @param id 训练器材/教材登记薄主键
     * @return 结果
     */
    public int deleteBdglEquipById(Long id);

    /**
     * 批量删除训练器材/教材登记薄
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglEquipByIds(Long[] ids);
    
    int selectlEquipCountByunitId(@Param("unitId") Integer unitId, @Param("year") String year);
    
    List<BdglEquip> selectEquIpByunitId(@Param("unitId") Integer unitId, @Param("year") String year);
}
