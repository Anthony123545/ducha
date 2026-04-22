package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglDrill;
import org.apache.ibatis.annotations.Param;

/**
 * 军事训练登记簿Mapper接口
 *
 * @author supervision
 * @date 2022-05-20
 */
public interface BdglDrillMapper
{
    /**
     * 查询军事训练登记簿
     *
     * @param id 军事训练登记簿主键
     * @return 军事训练登记簿
     */
    public BdglDrill selectBdglDrillById(Integer id);

    /**
     * 查询军事训练登记簿列表
     *
     * @param bdglDrill 军事训练登记簿
     * @return 军事训练登记簿集合
     */
    public List<BdglDrill> selectBdglDrillList(BdglDrill bdglDrill);

    /**
     * 新增军事训练登记簿
     *
     * @param bdglDrill 军事训练登记簿
     * @return 结果
     */
    public int insertBdglDrill(BdglDrill bdglDrill);

    /**
     * 修改军事训练登记簿
     *
     * @param bdglDrill 军事训练登记簿
     * @return 结果
     */
    public int updateBdglDrill(BdglDrill bdglDrill);

    /**
     * 删除军事训练登记簿
     *
     * @param id 军事训练登记簿主键
     * @return 结果
     */
    public int deleteBdglDrillById(Integer id);

    /**
     * 批量删除军事训练登记簿
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglDrillByIds(Integer[] ids);
    
    int selectlDrillCountByunitId(@Param("unitId") int unitId, @Param("year") String year);
    
    List<BdglDrill> selectDrillByUnitId(@Param("unitId") int unitId, @Param("year") String year);
}