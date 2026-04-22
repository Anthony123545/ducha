package com.supervision.phone.service;

import java.util.List;
import com.supervision.phone.domain.PhoneGrid;

/**
 * 手机柜格子Service接口
 * 
 * @author supervision
 * @date 2022-04-20
 */
public interface IPhoneGridService 
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
     * 批量删除手机柜格子
     * 
     * @param ids 需要删除的手机柜格子主键集合
     * @return 结果
     */
    public int deletePhoneGridByIds(Long[] ids);

    /**
     * 删除手机柜格子信息
     * 
     * @param id 手机柜格子主键
     * @return 结果
     */
    public int deletePhoneGridById(Long id);
}
