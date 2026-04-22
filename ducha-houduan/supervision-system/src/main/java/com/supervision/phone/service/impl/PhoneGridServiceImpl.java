package com.supervision.phone.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.phone.mapper.PhoneGridMapper;
import com.supervision.phone.domain.PhoneGrid;
import com.supervision.phone.service.IPhoneGridService;

/**
 * 手机柜格子Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-20
 */
@Service
public class PhoneGridServiceImpl implements IPhoneGridService 
{
    @Autowired
    private PhoneGridMapper phoneGridMapper;

    /**
     * 查询手机柜格子
     * 
     * @param id 手机柜格子主键
     * @return 手机柜格子
     */
    @Override
    public PhoneGrid selectPhoneGridById(Long id)
    {
        return phoneGridMapper.selectPhoneGridById(id);
    }

    /**
     * 查询手机柜格子列表
     * 
     * @param phoneGrid 手机柜格子
     * @return 手机柜格子
     */
    @Override
    public List<PhoneGrid> selectPhoneGridList(PhoneGrid phoneGrid)
    {
        return phoneGridMapper.selectPhoneGridList(phoneGrid);
    }

    /**
     * 新增手机柜格子
     * 
     * @param phoneGrid 手机柜格子
     * @return 结果
     */
    @Override
    public int insertPhoneGrid(PhoneGrid phoneGrid)
    {
        return phoneGridMapper.insertPhoneGrid(phoneGrid);
    }

    /**
     * 修改手机柜格子
     * 
     * @param phoneGrid 手机柜格子
     * @return 结果
     */
    @Override
    public int updatePhoneGrid(PhoneGrid phoneGrid)
    {
        return phoneGridMapper.updatePhoneGrid(phoneGrid);
    }

    /**
     * 批量删除手机柜格子
     * 
     * @param ids 需要删除的手机柜格子主键
     * @return 结果
     */
    @Override
    public int deletePhoneGridByIds(Long[] ids)
    {
        return phoneGridMapper.deletePhoneGridByIds(ids);
    }

    /**
     * 删除手机柜格子信息
     * 
     * @param id 手机柜格子主键
     * @return 结果
     */
    @Override
    public int deletePhoneGridById(Long id)
    {
        return phoneGridMapper.deletePhoneGridById(id);
    }
}
