package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglFoodBoardWages;

/**
 * 交/退伙食费Mapper接口
 * 
 * @author supervision
 * @date 2022-02-28
 */
public interface BdglFoodBoardWagesMapper 
{
    /**
     * 查询交/退伙食费
     * 
     * @param id 交/退伙食费主键
     * @return 交/退伙食费
     */
    public BdglFoodBoardWages selectBdglFoodBoardWagesById(Integer id);

    /**
     * 查询交/退伙食费列表
     * 
     * @param bdglFoodBoardWages 交/退伙食费
     * @return 交/退伙食费集合
     */
    public List<BdglFoodBoardWages> selectBdglFoodBoardWagesList(BdglFoodBoardWages bdglFoodBoardWages);

    /**
     * 新增交/退伙食费
     * 
     * @param bdglFoodBoardWages 交/退伙食费
     * @return 结果
     */
    public int insertBdglFoodBoardWages(BdglFoodBoardWages bdglFoodBoardWages);

    /**
     * 修改交/退伙食费
     * 
     * @param bdglFoodBoardWages 交/退伙食费
     * @return 结果
     */
    public int updateBdglFoodBoardWages(BdglFoodBoardWages bdglFoodBoardWages);

    /**
     * 删除交/退伙食费
     * 
     * @param id 交/退伙食费主键
     * @return 结果
     */
    public int deleteBdglFoodBoardWagesById(Integer id);

    /**
     * 批量删除交/退伙食费
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglFoodBoardWagesByIds(Integer[] ids);

    int deleteBdglFoodBoardWages(Integer id);
}
