package com.supervision.barracksManagement.service.impl;

import java.util.List;

import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.barracksManagement.mapper.SingleDormitoryMapper;
import com.supervision.barracksManagement.domain.SingleDormitory;
import com.supervision.barracksManagement.service.ISingleDormitoryService;

/**
 * 单身干部宿舍Service业务层处理
 * 
 * @author gaoqiang
 * @date 2022-11-19
 */
@Service
public class SingleDormitoryServiceImpl implements ISingleDormitoryService 
{
    @Autowired
    private SingleDormitoryMapper singleDormitoryMapper;

    /**
     * 查询单身干部宿舍
     * 
     * @param id 单身干部宿舍主键
     * @return 单身干部宿舍
     */
    @Override
    public SingleDormitory selectSingleDormitoryById(Long id)
    {
        return singleDormitoryMapper.selectSingleDormitoryById(id);
    }

    /**
     * 查询单身干部宿舍列表
     * 
     * @param singleDormitory 单身干部宿舍
     * @return 单身干部宿舍
     */
    @Override
    public List<SingleDormitory> selectSingleDormitoryList(SingleDormitory singleDormitory)
    {
        return singleDormitoryMapper.selectSingleDormitoryList(singleDormitory);
    }

    /**
     * 新增单身干部宿舍
     * 
     * @param singleDormitory 单身干部宿舍
     * @return 结果
     */
    @Override
    public int insertSingleDormitory(SingleDormitory singleDormitory)
    {
        singleDormitory.setCreateTime(DateUtils.getNowDate());
        return singleDormitoryMapper.insertSingleDormitory(singleDormitory);
    }

    /**
     * 修改单身干部宿舍
     * 
     * @param singleDormitory 单身干部宿舍
     * @return 结果
     */
    @Override
    public int updateSingleDormitory(SingleDormitory singleDormitory)
    {
        singleDormitory.setUpdateTime(DateUtils.getNowDate());
        return singleDormitoryMapper.updateSingleDormitory(singleDormitory);
    }

    /**
     * 批量删除单身干部宿舍
     * 
     * @param ids 需要删除的单身干部宿舍主键
     * @return 结果
     */
    @Override
    public int deleteSingleDormitoryByIds(Long[] ids)
    {
        return singleDormitoryMapper.deleteSingleDormitoryByIds(ids);
    }

    /**
     * 删除单身干部宿舍信息
     * 
     * @param id 单身干部宿舍主键
     * @return 结果
     */
    @Override
    public int deleteSingleDormitoryById(Long id)
    {
        return singleDormitoryMapper.deleteSingleDormitoryById(id);
    }
}
