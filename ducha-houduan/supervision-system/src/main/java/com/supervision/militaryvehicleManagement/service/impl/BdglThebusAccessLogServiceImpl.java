package com.supervision.militaryvehicleManagement.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.militaryvehicleManagement.mapper.BdglThebusAccessLogMapper;
import com.supervision.militaryvehicleManagement.domain.BdglThebusAccessLog;
import com.supervision.militaryvehicleManagement.service.IBdglThebusAccessLogService;

/**
 * 军车进出查询Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-09
 */
@Service
public class BdglThebusAccessLogServiceImpl implements IBdglThebusAccessLogService 
{
    @Autowired
    private BdglThebusAccessLogMapper bdglThebusAccessLogMapper;

    /**
     * 查询军车进出查询
     * 
     * @param id 军车进出查询主键
     * @return 军车进出查询
     */
    @Override
    public BdglThebusAccessLog selectBdglThebusAccessLogById(Integer id)
    {
        return bdglThebusAccessLogMapper.selectBdglThebusAccessLogById(id);
    }

    /**
     * 查询军车进出查询列表
     * 
     * @param bdglThebusAccessLog 军车进出查询
     * @return 军车进出查询
     */
    @Override
    public List<BdglThebusAccessLog> selectBdglThebusAccessLogList(BdglThebusAccessLog bdglThebusAccessLog)
    {
        return bdglThebusAccessLogMapper.selectBdglThebusAccessLogList(bdglThebusAccessLog);
    }

    /**
     * 新增军车进出查询
     * 
     * @param bdglThebusAccessLog 军车进出查询
     * @return 结果
     */
    @Override
    public int insertBdglThebusAccessLog(BdglThebusAccessLog bdglThebusAccessLog)
    {
        return bdglThebusAccessLogMapper.insertBdglThebusAccessLog(bdglThebusAccessLog);
    }

    /**
     * 修改军车进出查询
     * 
     * @param bdglThebusAccessLog 军车进出查询
     * @return 结果
     */
    @Override
    public int updateBdglThebusAccessLog(BdglThebusAccessLog bdglThebusAccessLog)
    {
        return bdglThebusAccessLogMapper.updateBdglThebusAccessLog(bdglThebusAccessLog);
    }

    /**
     * 批量删除军车进出查询
     * 
     * @param ids 需要删除的军车进出查询主键
     * @return 结果
     */
    @Override
    public int deleteBdglThebusAccessLogByIds(Integer[] ids)
    {
        return bdglThebusAccessLogMapper.deleteBdglThebusAccessLogByIds(ids);
    }

    /**
     * 删除军车进出查询信息
     * 
     * @param id 军车进出查询主键
     * @return 结果
     */
    @Override
    public int deleteBdglThebusAccessLogById(Integer id)
    {
        return bdglThebusAccessLogMapper.deleteBdglThebusAccessLogById(id);
    }

    @Override
    public List<BdglThebusAccessLog> selectBdglThebusAccessLogLists(BdglThebusAccessLog bdglThebusAccessLog) {
        return bdglThebusAccessLogMapper.selectBdglThebusAccessLogLists(bdglThebusAccessLog);
    }
}
