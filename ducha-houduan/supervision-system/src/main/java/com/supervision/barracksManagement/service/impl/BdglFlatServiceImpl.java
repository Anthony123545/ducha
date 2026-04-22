package com.supervision.barracksManagement.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.barracksManagement.mapper.BdglFlatMapper;
import com.supervision.barracksManagement.domain.BdglFlat;
import com.supervision.barracksManagement.service.IBdglFlatService;

/**
 * 公寓房档案Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-02
 */
@Service
public class BdglFlatServiceImpl implements IBdglFlatService 
{
    @Autowired
    private BdglFlatMapper bdglFlatMapper;

    /**
     * 查询公寓房档案
     * 
     * @param id 公寓房档案主键
     * @return 公寓房档案
     */
    @Override
    public BdglFlat selectBdglFlatById(Integer id)
    {
        return bdglFlatMapper.selectBdglFlatById(id);
    }

    /**
     * 查询公寓房档案列表
     * 
     * @param bdglFlat 公寓房档案
     * @return 公寓房档案
     */
    @Override
    public List<BdglFlat> selectBdglFlatList(BdglFlat bdglFlat)
    {
        return bdglFlatMapper.selectBdglFlatList(bdglFlat);
    }

    /**
     * 新增公寓房档案
     * 
     * @param bdglFlat 公寓房档案
     * @return 结果
     */
    @Override
    public int insertBdglFlat(BdglFlat bdglFlat)
    {
        bdglFlat.setCreatetime(new Date());
        return bdglFlatMapper.insertBdglFlat(bdglFlat);
    }

    /**
     * 修改公寓房档案
     * 
     * @param bdglFlat 公寓房档案
     * @return 结果
     */
    @Override
    public int updateBdglFlat(BdglFlat bdglFlat)
    {
        return bdglFlatMapper.updateBdglFlat(bdglFlat);
    }

    /**
     * 批量删除公寓房档案
     * 
     * @param ids 需要删除的公寓房档案主键
     * @return 结果
     */
    @Override
    public int deleteBdglFlatByIds(Integer[] ids)
    {
        return bdglFlatMapper.deleteBdglFlatByIds(ids);
    }

    /**
     * 删除公寓房档案信息
     * 
     * @param id 公寓房档案主键
     * @return 结果
     */
    @Override
    public int deleteBdglFlatById(Integer id)
    {
        return bdglFlatMapper.deleteBdglFlatById(id);
    }
}
