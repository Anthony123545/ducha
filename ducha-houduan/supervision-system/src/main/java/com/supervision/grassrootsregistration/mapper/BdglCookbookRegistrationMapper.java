package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglCookbookRegistration;

/**
 * 食谱登记Mapper接口
 * 
 * @author supervision
 * @date 2022-03-02
 */
public interface BdglCookbookRegistrationMapper 
{
    /**
     * 查询食谱登记
     * 
     * @param id 食谱登记主键
     * @return 食谱登记
     */
    public BdglCookbookRegistration selectBdglCookbookRegistrationById(Integer id);

    /**
     * 查询食谱登记列表
     * 
     * @param bdglCookbookRegistration 食谱登记
     * @return 食谱登记集合
     */
    public List<BdglCookbookRegistration> selectBdglCookbookRegistrationList(BdglCookbookRegistration bdglCookbookRegistration);

    /**
     * 新增食谱登记
     * 
     * @param bdglCookbookRegistration 食谱登记
     * @return 结果
     */
    public int insertBdglCookbookRegistration(BdglCookbookRegistration bdglCookbookRegistration);

    /**
     * 修改食谱登记
     * 
     * @param bdglCookbookRegistration 食谱登记
     * @return 结果
     */
    public int updateBdglCookbookRegistration(BdglCookbookRegistration bdglCookbookRegistration);

    /**
     * 删除食谱登记
     * 
     * @param id 食谱登记主键
     * @return 结果
     */
    public int deleteBdglCookbookRegistrationById(Integer id);

    /**
     * 批量删除食谱登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglCookbookRegistrationByIds(Integer[] ids);
}
