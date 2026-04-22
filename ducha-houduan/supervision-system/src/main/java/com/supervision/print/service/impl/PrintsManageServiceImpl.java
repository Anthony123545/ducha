package com.supervision.print.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.print.mapper.PrintsManageMapper;
import com.supervision.print.domain.PrintsManage;
import com.supervision.print.service.IPrintsManageService;

/**
 * 集中文印主Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-18
 */
@Service
public class PrintsManageServiceImpl implements IPrintsManageService 
{
    @Autowired
    private PrintsManageMapper printsManageMapper;

    /**
     * 查询集中文印主
     * 
     * @param id 集中文印主主键
     * @return 集中文印主
     */
    @Override
    public PrintsManage selectPrintsManageById(Long id)
    {
        return printsManageMapper.selectPrintsManageById(id);
    }

    /**
     * 查询集中文印主列表
     * 
     * @param printsManage 集中文印主
     * @return 集中文印主
     */
    @Override
    public List<PrintsManage> selectPrintsManageList(PrintsManage printsManage)
    {
        return printsManageMapper.selectPrintsManageList(printsManage);
    }

    /**
     * 新增集中文印主
     * 
     * @param printsManage 集中文印主
     * @return 结果
     */
    @Override
    public int insertPrintsManage(PrintsManage printsManage)
    {
        return printsManageMapper.insertPrintsManage(printsManage);
    }

    /**
     * 修改集中文印主
     * 
     * @param printsManage 集中文印主
     * @return 结果
     */
    @Override
    public int updatePrintsManage(PrintsManage printsManage)
    {
        return printsManageMapper.updatePrintsManage(printsManage);
    }

    /**
     * 批量删除集中文印主
     * 
     * @param ids 需要删除的集中文印主主键
     * @return 结果
     */
    @Override
    public int deletePrintsManageByIds(Long[] ids)
    {
        return printsManageMapper.deletePrintsManageByIds(ids);
    }

    /**
     * 删除集中文印主信息
     * 
     * @param id 集中文印主主键
     * @return 结果
     */
    @Override
    public int deletePrintsManageById(Long id)
    {
        return printsManageMapper.deletePrintsManageById(id);
    }
}
