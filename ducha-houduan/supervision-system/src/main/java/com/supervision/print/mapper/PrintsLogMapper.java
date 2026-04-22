package com.supervision.print.mapper;

import java.util.List;
import java.util.Map;

import com.supervision.print.domain.PrintsLog;
import org.apache.ibatis.annotations.Param;

/**
 * 集中文印记录Mapper接口
 * 
 * @author supervision
 * @date 2022-03-18
 */
public interface PrintsLogMapper 
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
     * 删除集中文印记录
     * 
     * @param id 集中文印记录主键
     * @return 结果
     */
    public int deletePrintsLogById(Long id);

    /**
     * 批量删除集中文印记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePrintsLogByIds(Long[] ids);

    /**
     * 查询出当月已归还和未归还的数据
     *
     * @param times
     * @param format2
     * @return
     */
     List<Map<String, Object>> selectPrintsLogByMouth(@Param("times")String times,@Param("format2") String format2);

    /**
     * 查询出无需归还的数据
     *
     * @param times
     * @param format2
     * @return
     */
     Long selectPringsLogByIsReturn(@Param("times")String times,@Param("format2") String format2);

    List<PrintsLog> selectPrintsLogLists( PrintsLog printsLog);

    List<PrintsLog> selectPrintsLogListss(PrintsLog printsLog);

    /**
     * 查询三天前需要提醒的数据
     * @param printsLog
     * @return
     */
    List<PrintsLog> selectprintremind(PrintsLog printsLog);


    Map<String, Object> selectCount(@Param("times")String times,@Param("format2") String format2);

    Map<String, Object> printCountss(PrintsLog printsLog);
}
