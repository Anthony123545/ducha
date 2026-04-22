package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglWeekworkRegister;

/**
 * 一周安排工作分Service接口
 * 
 * @author supervision
 * @date 2022-02-28
 */
public interface IBdglWeekworkRegisterService 
{
    /**
     * 查询一周安排工作分
     * 
     * @param id 一周安排工作分主键
     * @return 一周安排工作分
     */
    public BdglWeekworkRegister selectBdglWeekworkRegisterById(Long id);

    /**
     * 查询一周安排工作分列表
     * 
     * @param bdglWeekworkRegister 一周安排工作分
     * @return 一周安排工作分集合
     */
    public List<BdglWeekworkRegister> selectBdglWeekworkRegisterList(BdglWeekworkRegister bdglWeekworkRegister);

    /**
     * 新增一周安排工作分
     * 
     * @param bdglWeekworkRegister 一周安排工作分
     * @return 结果
     */
    public int insertBdglWeekworkRegister(BdglWeekworkRegister bdglWeekworkRegister);

    /**
     * 修改一周安排工作分
     * 
     * @param bdglWeekworkRegister 一周安排工作分
     * @return 结果
     */
    public int updateBdglWeekworkRegister(BdglWeekworkRegister bdglWeekworkRegister);

    /**
     * 批量删除一周安排工作分
     * 
     * @param ids 需要删除的一周安排工作分主键集合
     * @return 结果
     */
    public int deleteBdglWeekworkRegisterByIds(Long[] ids);

    /**
     * 删除一周安排工作分信息
     * 
     * @param id 一周安排工作分主键
     * @return 结果
     */
    public int deleteBdglWeekworkRegisterById(Long id);

    List<BdglWeekworkRegister> selectBdglWeekworkRegisterLists(BdglWeekworkRegister bdglWeekworkRegister);
}
