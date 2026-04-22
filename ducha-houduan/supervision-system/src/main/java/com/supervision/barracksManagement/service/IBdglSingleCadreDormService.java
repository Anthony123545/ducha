package com.supervision.barracksManagement.service;

import com.supervision.barracksManagement.domain.BdglSingleCadreDorm;

import java.util.List;

/**
 * 单身干部宿舍Service接口
 * 
 * @author ruoyi
 * @date 2022-08-08
 */
public interface IBdglSingleCadreDormService 
{
    /**
     * 查询单身干部宿舍
     * 
     * @param id 单身干部宿舍主键
     * @return 单身干部宿舍
     */
    public BdglSingleCadreDorm selectBdglSingleCadreDormById(Integer id);

    /**
     * 查询单身干部宿舍列表
     * 
     * @param bdglSingleCadreDorm 单身干部宿舍
     * @return 单身干部宿舍集合
     */
    public List<BdglSingleCadreDorm> selectBdglSingleCadreDormList(BdglSingleCadreDorm bdglSingleCadreDorm);

    /**
     * 新增单身干部宿舍
     * 
     * @param bdglSingleCadreDorm 单身干部宿舍
     * @return 结果
     */
    public int insertBdglSingleCadreDorm(BdglSingleCadreDorm bdglSingleCadreDorm);

    /**
     * 修改单身干部宿舍
     * 
     * @param bdglSingleCadreDorm 单身干部宿舍
     * @return 结果
     */
    public int updateBdglSingleCadreDorm(BdglSingleCadreDorm bdglSingleCadreDorm);

    /**
     * 批量删除单身干部宿舍
     * 
     * @param ids 需要删除的单身干部宿舍主键集合
     * @return 结果
     */
    public int deleteBdglSingleCadreDormByIds(Integer[] ids);

    /**
     * 删除单身干部宿舍信息
     * 
     * @param id 单身干部宿舍主键
     * @return 结果
     */
    public int deleteBdglSingleCadreDormById(Integer id);
}
