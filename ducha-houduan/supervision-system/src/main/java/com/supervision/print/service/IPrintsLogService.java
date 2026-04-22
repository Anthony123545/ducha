package com.supervision.print.service;

import java.util.List;
import java.util.Map;

import com.supervision.print.domain.PrintsLog;

/**
 * 集中文印记录Service接口
 * 
 * @author supervision
 * @date 2022-03-18
 */
public interface IPrintsLogService 
{
    /**
     * 查询集中文印记录
     * 
     * @param id 集中文印记录主键
     * @return 集中文印记录
     */
    public PrintsLog selectPrintsLogById(Long id);

    /**
     * 查询集中文印记录列表
     * 
     * @param printsLog 集中文印记录
     * @return 集中文印记录集合
     */
    public List<PrintsLog> selectPrintsLogList(PrintsLog printsLog);

    /**
     * 新增集中文印记录
     * 
     * @param printsLog 集中文印记录
     * @return 结果
     */
    public int insertPrintsLog(PrintsLog printsLog);

    /**
     * 修改集中文印记录
     * 
     * @param printsLog 集中文印记录
     * @return 结果
     */
    public int updatePrintsLog(PrintsLog printsLog);

    /**
     * 批量删除集中文印记录
     * 
     * @param ids 需要删除的集中文印记录主键集合
     * @return 结果
     */
    public int deletePrintsLogByIds(Long[] ids);

    /**
     * 删除集中文印记录信息
     * 
     * @param id 集中文印记录主键
     * @return 结果
     */
    public int deletePrintsLogById(Long id);

    /**
     * 查询出当月已归还和未归还的数据
     *
     * @param times
     * @param format2
     * @return
     */
    public List<Map<String, Object>> selectPrintsLogByMouth(String times, String format2);

    /**
     * 查询出无需归还的数据
     *
     * @param times
     * @param format2
     * @return
     */
    Long selectPringsLogByIsReturn(String times, String format2);

    List<PrintsLog> selectPrintsLogLists(PrintsLog printsLog);

    List<PrintsLog> selectPrintsLogListss(PrintsLog printsLog);

    Map<String, Object> selectCount(String format1, String format2);

    List<PrintsLog> selectprintremind(PrintsLog printsLog);


    Map<String, Object> printCountss(PrintsLog printsLog);
}
