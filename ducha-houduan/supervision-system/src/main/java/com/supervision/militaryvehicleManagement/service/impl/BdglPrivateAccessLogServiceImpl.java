package com.supervision.militaryvehicleManagement.service.impl;

import java.util.List;

import com.supervision.militaryvehicleManagement.domain.vo.PersonalCarStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.militaryvehicleManagement.mapper.BdglPrivateAccessLogMapper;
import com.supervision.militaryvehicleManagement.domain.BdglPrivateAccessLog;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateAccessLogService;

/**
 * 私家车进出查询Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-09
 */
@Service
public class BdglPrivateAccessLogServiceImpl implements IBdglPrivateAccessLogService 
{
    @Autowired
    private BdglPrivateAccessLogMapper bdglPrivateAccessLogMapper;

    /**
     * 查询私家车进出查询
     * 
     * @param id 私家车进出查询主键
     * @return 私家车进出查询
     */
    @Override
    public BdglPrivateAccessLog selectBdglPrivateAccessLogById(Integer id)
    {
        return bdglPrivateAccessLogMapper.selectBdglPrivateAccessLogById(id);
    }

    /**
     * 查询私家车进出查询列表
     * 
     * @param bdglPrivateAccessLog 私家车进出查询
     * @return 私家车进出查询
     */
    @Override
    public List<BdglPrivateAccessLog> selectBdglPrivateAccessLogList(BdglPrivateAccessLog bdglPrivateAccessLog)
    {
        return bdglPrivateAccessLogMapper.selectBdglPrivateAccessLogList(bdglPrivateAccessLog);
    }

    /**
     * 新增私家车进出查询
     * 
     * @param bdglPrivateAccessLog 私家车进出查询
     * @return 结果
     */
    @Override
    public int insertBdglPrivateAccessLog(BdglPrivateAccessLog bdglPrivateAccessLog)
    {
        return bdglPrivateAccessLogMapper.insertBdglPrivateAccessLog(bdglPrivateAccessLog);
    }

    /**
     * 修改私家车进出查询
     * 
     * @param bdglPrivateAccessLog 私家车进出查询
     * @return 结果
     */
    @Override
    public int updateBdglPrivateAccessLog(BdglPrivateAccessLog bdglPrivateAccessLog)
    {
        return bdglPrivateAccessLogMapper.updateBdglPrivateAccessLog(bdglPrivateAccessLog);
    }

    /**
     * 批量删除私家车进出查询
     * 
     * @param ids 需要删除的私家车进出查询主键
     * @return 结果
     */
    @Override
    public int deleteBdglPrivateAccessLogByIds(Integer[] ids)
    {
        return bdglPrivateAccessLogMapper.deleteBdglPrivateAccessLogByIds(ids);
    }

    /**
     * 删除私家车进出查询信息
     * 
     * @param id 私家车进出查询主键
     * @return 结果
     */
    @Override
    public int deleteBdglPrivateAccessLogById(Integer id)
    {
        return bdglPrivateAccessLogMapper.deleteBdglPrivateAccessLogById(id);
    }

    @Override
    public List<BdglPrivateAccessLog> selectBdglPrivateAccessLogLists(BdglPrivateAccessLog privateAccessLog) {
        return bdglPrivateAccessLogMapper.selectBdglPrivateAccessLogLists(privateAccessLog);
    }

    @Override
    public List<BdglPrivateAccessLog> selectBdglPrivateAccessLog(BdglPrivateAccessLog privateAccessLog1) {
        return bdglPrivateAccessLogMapper.selectBdglPrivateAccessLog(privateAccessLog1);
    }
    
    @Override
    public List<PersonalCarStat> selectPersonalCarInStat(PersonalCarStat personalCarStat) {
        return bdglPrivateAccessLogMapper.selectPersonalCarInStat(personalCarStat);
    }
    
    @Override
    public Long selectPersonalCarOutStat(PersonalCarStat personalCarStat) {
        return bdglPrivateAccessLogMapper.selectPersonalCarOutStat(personalCarStat);
    }
}
