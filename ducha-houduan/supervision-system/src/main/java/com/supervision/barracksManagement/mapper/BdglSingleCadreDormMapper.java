package com.supervision.barracksManagement.mapper;

import com.supervision.barracksManagement.domain.BdglSingleCadreDorm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 单身干部宿舍Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-08
 */
@Mapper
public interface BdglSingleCadreDormMapper 
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
     * 删除单身干部宿舍
     * 
     * @param id 单身干部宿舍主键
     * @return 结果
     */
    public int deleteBdglSingleCadreDormById(Integer id);

    /**
     * 批量删除单身干部宿舍
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglSingleCadreDormByIds(Integer[] ids);
}
