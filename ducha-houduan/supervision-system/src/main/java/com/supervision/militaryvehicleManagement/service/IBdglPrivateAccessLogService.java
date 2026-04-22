package com.supervision.militaryvehicleManagement.service;

import java.util.List;
import com.supervision.militaryvehicleManagement.domain.BdglPrivateAccessLog;
import com.supervision.militaryvehicleManagement.domain.vo.PersonalCarStat;

/**
 * 私家车进出查询Service接口
 * 
 * @author supervision
 * @date 2022-03-09
 */
public interface IBdglPrivateAccessLogService 
{
    /**
     * 查询私家车进出查询
     * 
     * @param id 私家车进出查询主键
     * @return 私家车进出查询
     */
    public BdglPrivateAccessLog selectBdglPrivateAccessLogById(Integer id);

    /**
     * 查询私家车进出查询列表
     * 
     * @param bdglPrivateAccessLog 私家车进出查询
     * @return 私家车进出查询集合
     */
    public List<BdglPrivateAccessLog> selectBdglPrivateAccessLogList(BdglPrivateAccessLog bdglPrivateAccessLog);

    /**
     * 新增私家车进出查询
     * 
     * @param bdglPrivateAccessLog 私家车进出查询
     * @return 结果
     */
    public int insertBdglPrivateAccessLog(BdglPrivateAccessLog bdglPrivateAccessLog);

    /**
     * 修改私家车进出查询
     * 
     * @param bdglPrivateAccessLog 私家车进出查询
     * @return 结果
     */
    public int updateBdglPrivateAccessLog(BdglPrivateAccessLog bdglPrivateAccessLog);

    /**
     * 批量删除私家车进出查询
     * 
     * @param ids 需要删除的私家车进出查询主键集合
     * @return 结果
     */
    public int deleteBdglPrivateAccessLogByIds(Integer[] ids);

    /**
     * 删除私家车进出查询信息
     * 
     * @param id 私家车进出查询主键
     * @return 结果
     */
    public int deleteBdglPrivateAccessLogById(Integer id);

    List<BdglPrivateAccessLog> selectBdglPrivateAccessLogLists(BdglPrivateAccessLog privateAccessLog);

    List<BdglPrivateAccessLog> selectBdglPrivateAccessLog(BdglPrivateAccessLog privateAccessLog1);
    
    List<PersonalCarStat> selectPersonalCarInStat(PersonalCarStat personalCarStat);
    
    Long selectPersonalCarOutStat(PersonalCarStat personalCarStat);
}
