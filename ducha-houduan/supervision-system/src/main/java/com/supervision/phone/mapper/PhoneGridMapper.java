package com.supervision.phone.mapper;

import java.util.List;
import com.supervision.phone.domain.PhoneGrid;

/**
 * 手机柜格子Mapper接口
 * 
 * @author supervision
 * @date 2022-04-20
 */
public interface PhoneGridMapper 
{
    /**
     * 查询手机柜格子
     * 
     * @param id 手机柜格子主键
     * @return 手机柜格子
     */
    public PhoneGrid selectPhoneGridById(Long id);

    /**
     * 查询手机柜格子列表
     * 
     * @param phoneGrid 手机柜格子
     * @return 手机柜格子集合
     */
    public List<PhoneGrid> selectPhoneGridList(PhoneGrid phoneGrid);

    /**
     * 新增手机柜格子
     * 
     * @param phoneGrid 手机柜格子
     * @return 结果
     */
    public int insertPhoneGrid(PhoneGrid phoneGrid);

    /**
     * 修改手机柜格子
     * 
     * @param phoneGrid 手机柜格子
     * @return 结果
     */
    public int updatePhoneGrid(PhoneGrid phoneGrid);

    /**
     * 删除手机柜格子
     * 
     * @param id 手机柜格子主键
     * @return 结果
     */
    public int deletePhoneGridById(Long id);

    /**
     * 批量删除手机柜格子
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneGridByIds(Long[] ids);
}
