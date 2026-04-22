package com.supervision.print.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.print.mapper.PrintsLogMapper;
import com.supervision.print.domain.PrintsLog;
import com.supervision.print.service.IPrintsLogService;

/**
 * 集中文印记录Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-18
 */
@Service
public class PrintsLogServiceImpl implements IPrintsLogService 
{
    @Autowired
    private PrintsLogMapper printsLogMapper;

    /**
     * 查询集中文印记录
     * 
     * @param id 集中文印记录主键
     * @return 集中文印记录
     */
    @Override
    public PrintsLog selectPrintsLogById(Long id)
    {
        return printsLogMapper.selectPrintsLogById(id);
    }

    /**
     * 查询集中文印记录列表
     * 
     * @param printsLog 集中文印记录
     * @return 集中文印记录
     */
    @Override
    public List<PrintsLog> selectPrintsLogList(PrintsLog printsLog)
    {
        return printsLogMapper.selectPrintsLogList(printsLog);
    }

    /**
     * 新增集中文印记录
     * 
     * @param printsLog 集中文印记录
     * @return 结果
     */
    @Override
    public int insertPrintsLog(PrintsLog printsLog)
    {
        return printsLogMapper.insertPrintsLog(printsLog);
    }

    /**
     * 修改集中文印记录
     * 
     * @param printsLog 集中文印记录
     * @return 结果
     */
    @Override
    public int updatePrintsLog(PrintsLog printsLog)
    {
        return printsLogMapper.updatePrintsLog(printsLog);
    }

    /**
     * 批量删除集中文印记录
     * 
     * @param ids 需要删除的集中文印记录主键
     * @return 结果
     */
    @Override
    public int deletePrintsLogByIds(Long[] ids)
    {
        return printsLogMapper.deletePrintsLogByIds(ids);
    }

    /**
     * 删除集中文印记录信息
     * 
     * @param id 集中文印记录主键
     * @return 结果
     */
    @Override
    public int deletePrintsLogById(Long id)
    {
        return printsLogMapper.deletePrintsLogById(id);
    }

    @Override
    public List<Map<String, Object>> selectPrintsLogByMouth(String times, String format2) {
        return printsLogMapper.selectPrintsLogByMouth(times,format2);
    }

    @Override
    public Long selectPringsLogByIsReturn(String times, String format2) {
        return printsLogMapper.selectPringsLogByIsReturn(times,format2);
    }

    @Override
    public List<PrintsLog> selectPrintsLogLists(PrintsLog printsLog) {
        return printsLogMapper.selectPrintsLogLists(printsLog);
    }

    @Override
    public List<PrintsLog> selectPrintsLogListss(PrintsLog printsLog) {
        return printsLogMapper.selectPrintsLogListss(printsLog);
    }

    @Override
    public Map<String, Object> selectCount(String times, String format2) {
        return printsLogMapper.selectCount(times,format2);
    }
    @Override
    public List<PrintsLog> selectprintremind(PrintsLog printsLog){
        return printsLogMapper.selectprintremind(printsLog);
    }

    @Override
    public Map<String, Object> printCountss(PrintsLog printsLog) {
        return printsLogMapper.printCountss(printsLog);
    }
}
