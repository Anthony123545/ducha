package com.supervision.print.mapper;

import java.util.List;
import com.supervision.print.domain.PrintsManage;

/**
 * 集中文印主Mapper接口
 * 
 * @author supervision
 * @date 2022-03-18
 */
public interface PrintsManageMapper 
{
    /**
     * 查询集中文印主
     * 
     * @param id 集中文印主主键
     * @return 集中文印主
     */
    public PrintsManage selectPrintsManageById(Long id);

    /**
     * 查询集中文印主列表
     * 
     * @param printsManage 集中文印主
     * @return 集中文印主集合
     */
    public List<PrintsManage> selectPrintsManageList(PrintsManage printsManage);

    /**
     * 新增集中文印主
     * 
     * @param printsManage 集中文印主
     * @return 结果
     */
    public int insertPrintsManage(PrintsManage printsManage);

    /**
     * 修改集中文印主
     * 
     * @param printsManage 集中文印主
     * @return 结果
     */
    public int updatePrintsManage(PrintsManage printsManage);

    /**
     * 删除集中文印主
     * 
     * @param id 集中文印主主键
     * @return 结果
     */
    public int deletePrintsManageById(Long id);

    /**
     * 批量删除集中文印主
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePrintsManageByIds(Long[] ids);
}
