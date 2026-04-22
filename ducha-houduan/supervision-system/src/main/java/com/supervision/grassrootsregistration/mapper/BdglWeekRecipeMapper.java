package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglWeekRecipe;

/**
 * 一周食谱 Mapper 接口
 *
 * @author supervision
 * @date 2026-03-27
 */
public interface BdglWeekRecipeMapper
{
    /**
     * 查询一周食谱
     *
     * @param id 一周食谱主键
     * @return 一周食谱
     */
    public BdglWeekRecipe selectBdglWeekRecipeById(Integer id);

    /**
     * 查询一周食谱列表
     *
     * @param bdglWeekRecipe 一周食谱
     * @return 一周食谱集合
     */
    public List<BdglWeekRecipe> selectBdglWeekRecipeList(BdglWeekRecipe bdglWeekRecipe);

    /**
     * 新增一周食谱
     *
     * @param bdglWeekRecipe 一周食谱
     * @return 结果
     */
    public int insertBdglWeekRecipe(BdglWeekRecipe bdglWeekRecipe);

    /**
     * 修改一周食谱
     *
     * @param bdglWeekRecipe 一周食谱
     * @return 结果
     */
    public int updateBdglWeekRecipe(BdglWeekRecipe bdglWeekRecipe);

    /**
     * 删除一周食谱
     *
     * @param id 一周食谱主键
     * @return 结果
     */
    public int deleteBdglWeekRecipeById(Integer id);

    /**
     * 批量删除一周食谱
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglWeekRecipeByIds(Integer[] ids);
}
