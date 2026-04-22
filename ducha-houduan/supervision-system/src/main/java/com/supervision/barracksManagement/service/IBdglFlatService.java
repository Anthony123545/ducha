package com.supervision.barracksManagement.service;

import java.util.List;
import com.supervision.barracksManagement.domain.BdglFlat;

/**
 * 公寓房档案Service接口
 * 
 * @author supervision
 * @date 2022-03-02
 */
public interface IBdglFlatService 
{
    /**
     * 查询公寓房档案
     * 
     * @param id 公寓房档案主键
     * @return 公寓房档案
     */
    public BdglFlat selectBdglFlatById(Integer id);

    /**
     * 查询公寓房档案列表
     * 
     * @param bdglFlat 公寓房档案
     * @return 公寓房档案集合
     */
    public List<BdglFlat> selectBdglFlatList(BdglFlat bdglFlat);

    /**
     * 新增公寓房档案
     * 
     * @param bdglFlat 公寓房档案
     * @return 结果
     */
    public int insertBdglFlat(BdglFlat bdglFlat);

    /**
     * 修改公寓房档案
     * 
     * @param bdglFlat 公寓房档案
     * @return 结果
     */
    public int updateBdglFlat(BdglFlat bdglFlat);

    /**
     * 批量删除公寓房档案
     * 
     * @param ids 需要删除的公寓房档案主键集合
     * @return 结果
     */
    public int deleteBdglFlatByIds(Integer[] ids);

    /**
     * 删除公寓房档案信息
     * 
     * @param id 公寓房档案主键
     * @return 结果
     */
    public int deleteBdglFlatById(Integer id);
}
