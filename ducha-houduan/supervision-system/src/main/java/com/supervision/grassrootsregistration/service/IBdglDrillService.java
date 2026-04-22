package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglDrill;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;

/**
 * 军事训练登记簿Service接口
 *
 * @author supervision
 * @date 2022-05-20
 */
public interface IBdglDrillService
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
     * 批量删除军事训练登记簿
     *
     * @param ids 需要删除的军事训练登记簿主键集合
     * @return 结果
     */
    public int deleteBdglDrillByIds(Integer[] ids);

    /**
     * 删除军事训练登记簿信息
     *
     * @param id 军事训练登记簿主键
     * @return 结果
     */
    public int deleteBdglDrillById(Integer id);
    
    List<SysDeptDto> selectDrillList(String year);
    
    List<BdglDrill> selectDrillByUnitId(Integer unitId,String year);
}